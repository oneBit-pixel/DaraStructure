package hashTab;

public class HashTabDemo {
    public static void main(String[] args) {

    }

    //表示一个雇员
    class Emp {
        public int id;
        public String name;
        public Emp next;//next 默认为Null

        public Emp(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    //创建EmpLinkedList,表示链表
    class EmpLinkedList {
        //头指针，指向第一个Emp,因此我们这个链表的head 是直接指向第一个Emp
        private Emp head;//默认为null

        //添加雇员到链表
        //说明
        //1.假定，当添加雇员时，id是子自增长，即id的分配总是从小到大
        // 因此我们将该雇员直接加入到本链表的最后即可
        public void add(Emp emp) {
            //如果是添加第一个雇员
            if (head == null) {
                head = emp;
                return;
            }
            //如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
            Emp curemp = head;
            while (true) {
                if (curemp.next == null) {//说明到链表最后了
                    break;
                }
                curemp = curemp.next;//后移
            }
            //退出时直接将emp 加入链表
            curemp.next = emp;
        }

        //遍历链表的雇员信息

        public void list() {
            if (head == null) {//说明链表为空
                System.out.println("当前链表为空...");
                return;
            }
            System.out.println("当前链表的信息为");
            Emp curEmp = head;//辅助指针
            while (true) {
                System.out.printf("=> id=%d name=%s \t", curEmp.id, curEmp.name);
                if (curEmp.next == null) {//说明curEmp已经是最后结点
                    break;
                }
                curEmp = curEmp.next;//后移,遍历
            }
            System.out.println();
        }
    }

    //创建HashTab 管理多条链表
    class HashTab{
        private EmpLinkedList[] empLinkedListArray;
        private int size;//表示有多少条链表

        //构造器
        public HashTab(int size){
            //初始化 empLinkedListArray
            empLinkedListArray=new EmpLinkedList[size];
            //
        }

        //添加雇员
        public void add(Emp emp){
            //根据员工的id,得到该员工应当添加到那条链表
            int empLinkedListNo = hashFun(emp.id);
            //将emp 添加到对应的链表中
            empLinkedListArray[empLinkedListNo].add(emp);
        }

        //遍历所有的链表,遍历hashTab
        public void list(){
            for (int i = 0; i < size; i++) {
                empLinkedListArray[i].list();
            }
        }

        //编写一个散列函数,使用一个简单的取模法
        public int hashFun(int id){
            return id % size;
        }
    }
}
