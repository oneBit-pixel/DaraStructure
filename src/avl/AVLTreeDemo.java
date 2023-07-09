package avl;


public class AVLTreeDemo {

    public static void main(String[] args) {
//        int[] arr = {4, 3, 6, 5, 7, 8};
//        int[] arr = {10, 12, 8, 9, 7, 6};
        int[] arr={10,11,7,6,8,9};

        //创建一个AVLTree对象
        AVLTree avtTree = new AVLTree();
        //添加结点
        for (int i = 0; i < arr.length; i++) {
            avtTree.add(new Node(arr[i]));
        }
        //遍历
        System.out.println("中序遍历");
        avtTree.infixOrder();

        System.out.println("在没有做平衡处理之前~~~");
        System.out.println("树的高度==>" + avtTree.getRoot().height());
        System.out.println("左子树的高度==>" + avtTree.getRoot().leftHeight());
        System.out.println("右子树树的高度==>" + avtTree.getRoot().rightHeight());


    }


}

//创建AVL Tree
class AVLTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    //查找要删除的结点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    //查找父节点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    //编写方法：
    //1. 返回 以node 为根结点的二叉排序树最小结点的值
    //2. 删除node 为根结点的二叉排序树的最小结点

    /**
     * @param node 传入的结点()
     * @return 返回的是 以Node 为根结点的二叉排序树的最小结点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        while (target.left != null) {
            target = target.left;
        }
        //这是 target就指向了最小结点
        //删除最小结点
        delNode(target.value);
        return target.value;
    }

    //删除结点
    public void delNode(int value) {
        if (root == null) {
            return;
        } else {
            //1.需求先去找要删除的结点 targetNode
            Node targetNode = search(value);
            //如果没有找到要删除的结点
            if (targetNode == null) {
                return;
            }
            //如果我们发现这颗二叉排序树只有一个结点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            //去找到targetNode的父节点
            Node parent = searchParent(value);
            //如果要删除的结点是叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                //判断targetNode 是父结点的左子结点，还是右子结点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;

                } else if (parent.right != null) {
                    parent.right = null;
                }

            } else if (targetNode.left != null && targetNode.right != null) {
                //删除有两颗子书的结点
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;

            } else {
                //删除只有一颗子树的结点
                if (targetNode.left != null) {
                    if (parent != null) {
                        //如果targetNode 是 parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        } else {
                            //targetNode 是 parent 的右子节点
                            parent.right = targetNode.left;
                        }
                    } else {
                        root = targetNode.left;
                    }

                } else {
                    //说明要删除的结点有右子节点
                    if (parent != null) {
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        } else {
                            //如果targetNode  是 parent 的右子节点
                            parent.right = targetNode.right;
                        }
                    } else {
                        root = targetNode.right;
                    }

                }
            }
        }
    }

    //添加结点的方法
    public void add(Node node) {
        if (root == null) {
            root = node; //如果root为空 则直接让root指向node
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空,不能遍历");
        }
    }
}


class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    //返回左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    //返回右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    //左旋转方法
    private void leftRotate() {
        //创建新的结点,以当前根结点的值
        Node newNode = new Node(value);
        //把新的结点左子树设置成当前结点的左子树
        newNode.left = left;
        //把新的结点的右子树设置成当前结点右子树的左子树
        newNode.right = right.left;
        //把当前结点的值替换成右子结点的值
        value = right.value;
        //把当前结点的右子树设置成右子树的右子树
        right = right.right;
        //把当前结点的左子树（左子节点）设置成新的结点
        left = newNode;

    }

    //右旋转
    private void rightRotate() {
        Node newNode = new Node(value);
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }

    //返回当前结点的高度,以该结点为根结点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    //查找要删除的节点

    /**
     * @param value 希望删除的结点的值
     * @return 如果找到返回该结点, 否则返回Null
     */
    public Node search(int value) {
        if (value == this.value) {
            //找到就是该结点
            return this;
        } else if (value < this.value) {
            //如果查找的值小于当前结点，向左子树递归查找
            //如果左子结点为空,
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {
            //如果札沼地值不小于当前结点，向右子树递归查找
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }

    }

    //查找要删除结点的父节点

    /**
     * @param value 要找的结点的值
     * @return 返回的是 要删除的结点的父节点 如果没有则返回null
     */
    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            //如果查找的值小于当前结点的值，并且当前结点的左子节点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value);//向右子树递归查找

            } else {
                return null;//没有找到父节点
            }
        }

    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    //添加节点的方法
    //递归的形式,需要满足二叉排序树的要求
    public void add(Node node) {
        if (node == null) {
            return;
        }
        //判断传入的节点,和当前子树的根节点的关系
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {//添加的值 大于 当前结点的值
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }

        //当添加玩一个结点后，如果：（右子树的高度-左子树的高度)>1.左旋转
        if (rightHeight() - leftHeight() > 1) {
            //如果它的右子树的左子树的高度大于它的右子树的右子树的高度
            if (right != null && right.leftHeight()>right.rightHeight()) {
                //先对右子节点进行右旋转
                right.rightRotate();
                //然后在对当前结点进行右旋转
                leftRotate();//左旋转

            }else {
                //先对右子节点
                leftRotate();//左旋转
            }
            return;//必须要
        }

        //当添加完一个结点后,如果（左子树的高度 - 右子树的高度)>1 ,右旋转
        if (leftHeight() - rightHeight() > 1) {
            //如果它的左子树的右子树大于它的左子树的高度
            if (left != null && left.rightHeight()>left.leftHeight()) {
                //先对当前结点的左结点（左子树） -> 左旋转
                left.leftRotate();
                //再对当前结点进行右旋转
                rightRotate();//右旋转
            }else {
                //直接进行右旋转即可
                rightRotate();//右旋转
            }
        }
    }


    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }
}
