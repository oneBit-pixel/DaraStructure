package tree;

public class BinaryTreeDemo {
    public static void main(String[] args) {
        //先创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建需要的结点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        //说明，我们先手动创建该二叉树，后面我们学习递归的方式创建二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setLeft(node5);
        node3.setRight(node4);
        binaryTree.setRoot(root);


        //测试
        System.out.println("前序遍历");//结果：1，2，3，4
        binaryTree.preOrder();

        System.out.println("中序遍历");//结果：2,1,3,4
        binaryTree.infixOrder();

        System.out.println("后序遍历");//结果：2,4,3,1
        binaryTree.postOrder();

        //前序遍历 4次
        System.out.println("前序遍历方式...");
        HeroNode preResNode = binaryTree.preOrderSearch(5);
        if (preResNode != null) {
            System.out.printf("找到了,信息为no=%d name%s为",preResNode.getNo(),preResNode.getName());
        }else {
            System.out.printf("没有找到 no = %d 的英雄",5);
        }

        //中序遍历 3次
        System.out.println("前序遍历方式...");
        HeroNode fixResNode = binaryTree.infixOrderSearch(5);
        if (fixResNode != null) {
            System.out.printf("找到了,信息为no=%d name%s为",fixResNode.getNo(),fixResNode.getName());
        }else {
            System.out.printf("没有找到 no = %d 的英雄",5);
        }

        //后序遍历
        System.out.println("后序遍历查找方式...");
        HeroNode postResNode = binaryTree.postOrderSearch(5);
        if (fixResNode != null) {
            System.out.printf("找到了,信息为no=%d name为%s \n",postResNode.getNo(),postResNode.getName());
        }else {
            System.out.printf("没有找到 no = %d 的英雄",5);
        }
    }
}

//定义BinaryTree 二叉树
class BinaryTree {
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
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
    public HeroNode preOrderSearch(int no){
        if (root != null) {
            return root.preOrderSearch(no);
        }else{
            return null;
        }
    }

    //中序遍历
    public HeroNode infixOrderSearch(int no){
        if (root != null) {
            return root.infixOrderSearch(no);
        }else {
            return null;
        }
    }

    //后序遍历
    public HeroNode postOrderSearch(int no){
        if (root != null) {
            return root.postOrderSearch(no);
        }else {
            return null;
        }
    }
}

//创建HeroNode 节点
class HeroNode {
    private int no;
    private String name;
    private HeroNode left;//默认为null
    private HeroNode right;//默认为null

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
                ", left=" + left +
                ", right=" + right +
                '}';
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
    public HeroNode infixOrderSearch(int no){
        HeroNode resNode=null;
        //判断当前结点的左子结点是否为空，如果不为空，则递归中序查找
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }
        if (resNode != null) {
           return resNode;
        }
        System.out.println("进入中序查找了...");
        //如果找到，则返回，如果没有找到，就和当前结点比较，如果是则返回当前结点
        if (this.no==no) {
            return this;
        }
        //否则继续进行右递归的中序查找
        if (this.right != null) {
            resNode=this.right.infixOrderSearch(no);
        }
        return resNode;
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no){
        //判断当前结点的左子结点是否为空，如果不为空，则递归后序查找
        HeroNode resNode=null;
        if (this.left!=null) {
            resNode=this.left.postOrderSearch(no);
        }
        if (resNode != null) {
            //说明在左子树找到
            return resNode;
        }
        //如果左子树，没有找到，则向右子树递归进行后序查找
        if (this.right!=null){
            resNode=this.right.postOrderSearch(no);
        }
        if (resNode != null) {
            //说明在右子树找到
            return resNode;
        }
        System.out.println("进入后序查找了...");
        //如果左右子树，都没有找到，就比较当前结点是不是
        if (this.no==no) {
            return this;
        }
        return null;
    }
}