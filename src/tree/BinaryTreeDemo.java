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
//        System.out.println("前序遍历");//结果：1，2，3，4
//        binaryTree.preOrder();
//
//        System.out.println("中序遍历");//结果：2,1,3,4
//        binaryTree.infixOrder();
//
//        System.out.println("后序遍历");//结果：2,4,3,1
//        binaryTree.postOrder();
//
//        //前序遍历 4次
//        System.out.println("前序遍历方式...");
//        HeroNode preResNode = binaryTree.preOrderSearch(5);
//        if (preResNode != null) {
//            System.out.printf("找到了,信息为no=%d name%s为", preResNode.getNo(), preResNode.getName());
//        } else {
//            System.out.printf("没有找到 no = %d 的英雄", 5);
//        }
//
//        //中序遍历 3次
//        System.out.println("前序遍历方式...");
//        HeroNode fixResNode = binaryTree.infixOrderSearch(5);
//        if (fixResNode != null) {
//            System.out.printf("找到了,信息为no=%d name%s为", fixResNode.getNo(), fixResNode.getName());
//        } else {
//            System.out.printf("没有找到 no = %d 的英雄", 5);
//        }
//
//        //后序遍历
//        System.out.println("后序遍历查找方式...");
//        HeroNode postResNode = binaryTree.postOrderSearch(5);
//        if (fixResNode != null) {
//            System.out.printf("找到了,信息为no=%d name为%s \n", postResNode.getNo(), postResNode.getName());
//        } else {
//            System.out.printf("没有找到 no = %d 的英雄", 5);
//        }

        //测试一把删除结点
        System.out.println("删除前,前序遍历");
        binaryTree.preOrder(); //1,2,3,5,4
//        binaryTree.delNode(5);
        binaryTree.delNode(3); //1,2
        System.out.println("删除后,前序遍历");
        binaryTree.preOrder();//1,2,3,5,4
    }
}



