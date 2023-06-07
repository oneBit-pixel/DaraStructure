package tree;

public class ThreadedBinaryTree {
    //创建HeroNode
    public static void main(String[] args) {
        //测试一把 中序线索二叉树的功能是否正确
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");

        //二叉树，后面我们要递归创建，现在简单处理使用手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        //测试线索化
        BinaryTree threadedBinaryTree = new BinaryTree();
        threadedBinaryTree.setRoot(root);
        threadedBinaryTree.threadedNodes();

        //测试：以10号结点去测试
        HeroNode leftNode = node5.getLeft();
        HeroNode rightNode = node5.getRight();
        System.out.println("10号的前驱结点是=" + leftNode);
        System.out.println("10号的后驱结点是=" + rightNode);
    }

}

//定义ThreadedBinaryTree 二叉树, 实现了线索化功能的二叉树
class BinaryTree {
    private HeroNode root;

    //为了实现线索化，需要创建要给指向当前结点的前驱结点的指针
    //在递归进行线索化时，Pre总是保留前一个结点
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void threadedNodes() {
        this.threadedNodes(root);
    }


    //编写二叉树进行中序线索化的方法

    /**
     * @param node 就是当前需要线索化的结点
     */
    public void threadedNodes(HeroNode node) {
        //如果 node == null, 不能线索化
        if (node == null) {
            return;
        }

        //（一） 先线索化我们的左子树
        threadedNodes(node.getLeft());
        //（二） 线索化当前结点

        //处理当前结点的前驱结点
        //以8结点来理解
        //8结点的left = null
        if (node.getLeft() == null) {
            //当前结点的左指针指向前驱结点
            node.setLeft(pre);
            //修改当前结点的左指针的类型，指向前驱结点
            node.setLeftType(1);
        }

        //处理后继节点
        if (pre != null && pre.getRight() == null) {
            //让前驱结点的右指针指向当前结点
            pre.setRight(node);
            //修改前驱结点的右指针类型
            pre.setRightType(1);
        }
        //!!! 每处理一个结点后，让当前结点是下一个结点的前驱结点
        pre = node;

        //（三） 再线索化右子树
        threadedNodes(node.getRight());
    }

    //删除结点
    public void delNode(int no) {
        if (root != null) {
            //如果只有一个root结点,这里立即判断root是不是就是要删除的结点
            if (root.getNo() == no) {
                root = null;
            } else {
                //递归删除
                root.delNode(no);
            }
        } else {
            System.out.println("空树,不能删除...");
        }
    }

    //前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空 无法遍历...");
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空 无法遍历...");

        }
    }

    //后序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空 无法遍历...");
        }
    }


    //前序遍历
    public HeroNode preOrderSearch(int no) {
        if (root != null) {
            return root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    //中序遍历
    public HeroNode infixOrderSearch(int no) {
        if (root != null) {
            return root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    //后序遍历
    public HeroNode postOrderSearch(int no) {
        if (root != null) {
            return root.postOrderSearch(no);
        } else {
            return null;
        }
    }
}

//先创建HeroNode 结点
//创建HeroNode 节点
class HeroNode {
    private int no;
    private String name;
    private HeroNode left;//默认为null
    private HeroNode right;//默认为null

    //说明
    //1. 如果leftType==0 表示指向左子树，如果 1 则表示指向前驱结点
    //2. 如果rightType==0 表示指向右子树,如果 1 表示指向后继结点
    private int leftType;
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //递归删除结点
    //1. 如果删除的节点是叶子节点，则删除改节点
    //2. 如果删除的节点是非叶子节点，则删除该子树
    public void delNode(int no) {
        //思路
        /**
         * 1. 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要则除结点，而不能去判断当前这个结点是不是需要删除结点，
         * 2. 如果当前结点的左子结点不为空，并且左子结点就是要除结点，就将this,left=nul1;并且就返回（结束递归则除）
         * 3. 如果当前结点的右子结点不为空，并且右子结点就是要则除结点，就将this,right=nul1;并且就返回（结束递归则除）
         * 4. 如果第2和第3步没有则除结点，那么我们就需要向左子树进行递归则除
         * 5. 如果第4步也没有删除结点，则应当向右子树进行递归刷除，
         */
        //2.如果当前结点的左子节点不为空，并且左子节点就说要删除的结点，就将this.left=null; 并且返回（结束递归删除)
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }
        // 3. 如果当前结点的右子结点不为空，并且右子结点就是要则除结点，就将this,right=nul1;并且就返回（结束递归则除）
        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }
        //4. 我们就需要向左子树进行递归则除
        if (this.left != null) {
            this.left.delNode(no);
        }
        //5.则应当向右子树进行递归删除
        if (this.right != null) {
            this.right.delNode(no);
        }
    }


    //编写前序遍历的方法
    public void preOrder() {
        System.out.println(this);//先输出父结点
        //递归向左子树 前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        //递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    //中序遍历
    public void infixOrder() {
        //递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        //输出父节点
        System.out.println(this);
        //递归向右子树中序遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    //后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }


    /**
     * 前序遍历查找
     *
     * @param no 查找 No
     * @return no
     */
    public HeroNode preOrderSearch(int no) {
        System.out.println("进入前序遍历了...");
        //比较当前
        if (this.no == no) {
            return this;
        }
        //1. 则判断当前的左子结点是否为空，如果不为空，则递归前序查找
        //2. 如果左递归前序查找，找到结点则返回
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }
        if (resNode != null) {
            //说明左子树，找到了
            return resNode;
        }
        //1.左递归前序查找，找到结点，则返回，否判断判断，
        //2.当前的结点右子结点是否为空，如果不为空，则继续向右递归前序查找
        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }
        return resNode;
    }

    //中序遍历查找
    public HeroNode infixOrderSearch(int no) {
        HeroNode resNode = null;
        //判断当前结点的左子结点是否为空，如果不为空，则递归中序查找
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("进入中序查找了...");
        //如果找到，则返回，如果没有找到，就和当前结点比较，如果是则返回当前结点
        if (this.no == no) {
            return this;
        }
        //否则继续进行右递归的中序查找
        if (this.right != null) {
            resNode = this.right.infixOrderSearch(no);
        }
        return resNode;
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no) {
        //判断当前结点的左子结点是否为空，如果不为空，则递归后序查找
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }
        if (resNode != null) {
            //说明在左子树找到
            return resNode;
        }
        //如果左子树，没有找到，则向右子树递归进行后序查找
        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null) {
            //说明在右子树找到
            return resNode;
        }
        System.out.println("进入后序查找了...");
        //如果左右子树，都没有找到，就比较当前结点是不是
        if (this.no == no) {
            return this;
        }
        return null;
    }
}