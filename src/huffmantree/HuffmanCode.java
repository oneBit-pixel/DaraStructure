package huffmantree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * 赫夫曼编码
 */
public class HuffmanCode {
    public static void main(String[] args) {

        //测试压缩文件
//        String scrFile = "d://1.txt";
//        String dstFile = "d://1.zip";
//
//        zipFile(scrFile, dstFile);
//        System.out.println("压缩成功");

        //测试解压文件
        String zipFile = "d://1.zip";
        String dstFile = "d://3.txt";
        unZipFile(zipFile, dstFile);
        System.out.println("解压成功");

        /*
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println(contentBytes.length);

        byte[] huffmanCodesBytes = hufffmanZip(contentBytes);
        System.out.println("压缩后的结果是：" + Arrays.toString(huffmanCodesBytes));
        System.out.println("长度为==>" + huffmanCodesBytes.length);
        //测试一把 byteToBitString 方法
//        System.out.println(byteToBitString());

        //测试解码方法
        System.out.println("huffmanCodes ==> " + huffmanCodes);
        byte[] decode = decode(huffmanCodes, huffmanCodesBytes);

        String s = new String(decode);
        System.out.println("返回的值 ==>" + s);//"i like like like java do you like a java"
         */

    }

    //编写一个方法，完成对压缩文件的压缩

    /**
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解药到的具体路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        //定义文件的输入流
        InputStream is = null;
        //定义一个对象输入流
        ObjectInputStream ois = null;
        //定义一个文件的输出流
        OutputStream os = null;
        try {
            //先创建文件输入流
            is = new FileInputStream(zipFile);
            //创建一个和is 关联的对象输入流
            ois = new ObjectInputStream(is);
            byte[] huffmanBytes = new byte[is.available()];
            //堆区Byte数组 huffmanBytes
            ois.read(huffmanBytes);
            //读取赫夫曼编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            //解码
            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            //将bytes 数组写入到目标文件
            os = Files.newOutputStream(Paths.get(dstFile));
            //写数据到文件中
            os.write(bytes);
        } catch (Exception e) {
            System.out.println("出错了1==>" + e.toString());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }else {
                    System.out.println("os为空");
                }
                if (ois != null) {
                    ois.close();
                }else {
                    System.out.println("ois为空");
                }
                if (is != null) {
                    is.close();
                }else {
                    System.out.println("is为空");
                }
            } catch (Exception e) {
                System.out.println("出错了2==>" + e.getMessage());
            }
        }
    }

    //编写一个方法，将一个文件进行压缩

    /**
     * @param scrFile 你传入的希望压缩的文件的全路径
     * @param desFile 我们压缩后将压缩文件放到那个目录下
     */
    public static void zipFile(String scrFile, String desFile) {
        //创建输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        //创建文件输入流
        FileInputStream is = null;

        try {
            //创建输出流
            is = new FileInputStream(scrFile);
            //创建一个和源文件大小一样的Byte[]
            byte[] b = new byte[is.available()];
            //读取文件
            is.read(b);
            //获取到文件对应的赫夫曼编码表
            byte[] huffmanBytes = hufffmanZip(b);
            //创建文件的输出流,存放压缩文件
            os = new FileOutputStream(desFile);

            //
            oos = new ObjectOutputStream(os);
            //写入赫夫曼编码,为了以后恢复源文件时使用
            oos.write(huffmanBytes);
            //再把赫夫曼编码写入 压缩文件
            oos.writeObject(huffmanCodes);

        } catch (Exception e) {
            System.out.println("1==>"+e.getMessage());
        } finally {
            try {
                is.close();
                oos.close();
                os.close();
            } catch (Exception e) {
                System.out.println("2==>"+e.getMessage());
            }
        }
    }

    //完成解压
    //思路
    //1. 将HuffmanCodesBytes [-88,-65,-56,...
    // 重写 先转成 赫夫曼编码对应的字符产 10101000...
    //2. 赫夫曼编码对应的二进制的字符串"10101000... => 对照 赫夫曼编码 =》
    // i like like like java do you like?

    //编写一个方法，完成对压缩数据的解码

    /**
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码的到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        //1. 先得到的 huffmanBytes 对应的 二进制的字符串形式10101000...
        StringBuilder stringBuilder = new StringBuilder();
        //将bytes 数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            //判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, b));
        }
//        System.out.println("赫夫曼字节数组对应的二进制字符串="+stringBuilder.toString());

        //把字符串按照指定的赫夫曼编码进行解码
        //把 赫夫曼编码表进行调换,因为反向查询 97 -> 100 100->a
        Map<String, Byte> map = new HashMap<String, Byte>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        //创建一个结合，存放Byte
        List<Byte> list = new ArrayList<>();
        // i 可以理解成就是索引，扫描stringBuilder
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte b = null;

            while (flag) {
                //1010100010111...
                //递增取出 key 1
                //取出'1' , '0'
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    //匹配到
                    flag = false;
                }
            }
            list.add(b);
            i += count;// i 直接移动到count
        }
        //当for循环结束后，我们的list中 存放了所有的字符 " i like like like java dou like a java"
        //把list 中的数据 放入到byte[] 并返回
        byte[] b = new byte[list.size()];

        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }

        return b;
    }

    /**
     * 将一个byte 转成一个二进制的字符串
     *
     * @param flag 标识是否需要补高位，如果是最后一个字节，无需补高位
     * @param b    传入的byte
     * @return 是该b 对应的二进制字符串，（注意是按补码返回)
     */
    private static String byteToBitString(boolean flag, byte b) {
        //使用变量保存b
        int temp = b;
        //如果是整数，还存在补高位的问题
        if (flag) {
            temp |= 256;//按位与256 1 0000 0000 => 1 0000 0001
        }

        String str = Integer.toBinaryString(temp);
        if (flag) {
            return str.substring(str.length() - 8);

        } else {
            return str;
        }

    }

    //编写一个方法，将字符串对应的Byte[]数组 ，通过生成的赫夫曼编码表，返回一个赫夫曼编码 压缩后的byte[]

    /**
     * @param bytes        这时原始的字串对应的byte[]
     * @param huffmanCodes 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的byte[]
     * huffmanCodeBytes[0] = 10101000(补码） => byte [ 推导 10101000 => 10101000 -1 => 10100111（反码) => 11011000]
     * huffmanCodeBytes[1] = -88
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        // 1. 利用huffmanCodes 将 bytes 转成 赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        // 遍历bytes 数字
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        System.out.println("测试 stringBuilder----- =" + huffmanCodes);

        //统计返回 byte[] huffmanCodeBytes 长度
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }
        //创建存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;//记录是第几个byte
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            //因为是每8位对应一个byte，所以步长+8
            String strByte;
            if (i + 8 > stringBuilder.length()) {
                //不够8位
                strByte = stringBuilder.substring(i);


            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }

            //将strByte 转成一个byte,放入到
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }
        return huffmanCodeBytes;
    }

    //生成赫夫曼树对应的赫夫曼编码
    //思路：
    //1. 将赫夫曼编码表存放在Map<Byte,String> 形式
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    //2. 在生成赫夫曼树编码表示，需要去拼接路径,定义一个StringBuilder 存储某个叶子节点路径
    static StringBuilder stringBuilder = new StringBuilder();

    //为了调用方便，我们重载getCodes
    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        //处理root的左子树
        getCodes(root.left, "0", stringBuilder);
        //处理root的右子树
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }


    //使用一个方法32，将所有方法全部封装起来，便于我们的调用

    /**
     * @param bytes 原始的字符串对应的字符数组
     * @return 是经过 赫夫曼编码处理后的字节数组(压缩过的)
     */
    private static byte[] hufffmanZip(byte[] bytes) {

        List<Node> nodes = getNodes(bytes);
        //根据nodes 创建赫夫曼树
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        //对应的赫夫曼编码(根据 赫夫曼编码)
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        //根据生成的赫夫曼编码，压缩得到压缩后的赫夫曼编码字节数组
        byte[] huffmanCodesBytes = zip(bytes, huffmanCodes);


        return huffmanCodesBytes;
    }

    /**
     * 功能： 将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
     *
     * @param node          传入结点，
     * @param code          路径： 左子节点是0，右子节点是1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code 加入到stringBuilder2
        stringBuilder2.append(code);
        if (node != null) {
            //如果Node == null 不处理
            //判断当前Node 是叶子结点还是非叶子结点
            if (node.data == null) {
                //递归处理
                //向左
                getCodes(node.left, "0", stringBuilder2);
                //向右递归
                getCodes(node.right, "1", stringBuilder2);
            } else {
                //说明是一个叶子结点
                //就表示找到某个叶子结点的最后
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }

        }
    }


    //前序遍历
    private static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("赫夫曼树为空");
        }

    }

    private static List<Node> getNodes(byte[] bytes) {
        //1. 创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();

        //遍历 bytes ,统计每一个byte出现的次数 -> map[key,value]
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {
                //Map 还有没有这个字符数据，第一次
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        //把每一个键值对转成一个Node 对象,并假如到nodes集
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;


    }

    //可以通过List创建对应的赫夫曼树
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            //排序，从小到大
            Collections.sort(nodes);
            //取出第一颗最小的二叉树
            Node leftNode = nodes.get(0);
            //取出第二颗最小的二叉树
            Node rightNode = nodes.get(1);
            //创建一颗新的二叉树,它的根节点，没有data，只有权值
            Node parent =
                    new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;

            //将已经处理的两颗二叉树从Nodes删除
            nodes.remove(leftNode);
            nodes.remove(rightNode);

            //将新的二叉树，加入到nodes
            nodes.add(parent);
        }
        //nodes 最后的节点，就是赫夫曼树的根节点
        return nodes.get(0);
    }

    //创建Node, 带数据和权值
    static class Node implements Comparable<Node> {
        Byte data; //存放数据（字符）本身，比如'a'=>97 ' '=>32
        char c;
        int weight;//权值，表示字符

        Node left; //

        Node right;//

        public Node(Byte data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            //从小到大排序
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", weight=" + weight +
                    '}';
        }

        //前序遍历
        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }
    }

}



