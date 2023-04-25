package hashTab;

import java.util.Scanner;

public class HashTabDemo {
    public static void main(String[] args) {
        //创建哈希表
        HashTab hashTab = new HashTab(7);
        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add：添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("exit:推出系统");
            System.out.println("find:查找雇员");
            System.out.println("delete:删除雇员");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;

                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "delete":
                    System.out.println("请输入要删除的id");
                    id = scanner.nextInt();
                    hashTab.deleteEmpById(id);
                    break;
                case "exit":
                    scanner.next();
                    System.exit(0);

                default:

                    break;
            }
        }
    }

    //表示一个雇员
    static class Emp {
        public int id;
        public String name;
        public Emp next;//next 默认为Null

        public Emp(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    //创建EmpLinkedList,表示链表
    static class EmpLinkedList {
        //头指针，指向第一个Emp,因此我们这个链表的head 是直接指向 第一个Emp
        //注意：是只指向 第一个Emp
        private Emp head;//默认为null

        public void delete(int id) {
            if (head == null) {
                System.out.println("删除失败..该链表为空");
                return;
            }
            //辅助指针
            Emp curemp = head;
            while (true) {
                if (head.id == id) {
                    System.out.println("删除中...");
                    head = head.next;
                    break;
                }
                if (curemp.next==null) {
                    break;
                }

                if (curemp.next.id==id) {
                    curemp.next=curemp.next.next;
                }
                curemp = curemp.next;
            }
        }

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
                if (curemp.id == emp.id) {
                    curemp.name = emp.name;
                    System.out.println("有重复的id，已经覆盖...");
                    return;
                }
                if (curemp.next == null) {//说明到链表最后了
                    break;
                }

                curemp = curemp.next;//后移
            }
            //退出时直接将emp 加入链表
            curemp.next = emp;
        }

        //遍历链表的雇员信息

        public void list(int no) {
            if (head == null) {//说明链表为空
                System.out.println("第" + (no + 1) + "链表为空...");
                return;
            }
            System.out.println("第" + (no + 1) + "链表的信息为");
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


        //根据id查雇员
        public Emp findEmpById(int id) {
            //判断链表为空
            if (head == null) {
                System.out.println("链表为空");
                return null;
            }
            //辅助指针
            Emp curEmp = head;
            while (true) {
                if (curEmp.id == id) {//找到

                    break;//这时curEmp.id就指向要查找的雇员
                }
                //退出
                if (curEmp.next == null) {//说明遍历当前链表没有找到该雇员
                    curEmp = null;
                    break;
                }
                curEmp = curEmp.next;
            }
            return curEmp;
        }


    }

    //创建HashTab 管理多条链表
    static class HashTab {
        private EmpLinkedList[] empLinkedListArray;
        private int size;//表示有多少条链表

        //构造器
        public HashTab(int size) {
            this.size = size;
            //初始化 empLinkedListArray
            empLinkedListArray = new EmpLinkedList[size];
            //
            for (int i = 0; i < size; i++) {
                empLinkedListArray[i] = new EmpLinkedList();
            }
        }

        //添加雇员
        public void add(Emp emp) {
            //根据员工的id,得到该员工应当添加到那条链表
            int empLinkedListNo = hashFun(emp.id);
            //将emp 添加到对应的链表中
            empLinkedListArray[empLinkedListNo].add(emp);
        }

        //遍历所有的链表,遍历hashTab
        public void list() {
            for (int i = 0; i < size; i++) {
                empLinkedListArray[i].list(i);
            }
        }

        //根据输入的id，查找雇员
        public void findEmpById(int id) {
            int No = hashFun(id);
            //使用散列函数确定到那条链表查找
            Emp emp = empLinkedListArray[No].findEmpById(id);
            if (emp != null) {
                System.out.printf("在第%d条链表中找到 雇员id=%d", (No + 1), id);
                System.out.println();
            } else {
                System.out.println("在哈希表中，没有找到该雇员~");
            }
        }

        //删除雇员
        public void deleteEmpById(int id) {
            //判断在那条链表
            int No = hashFun(id);
            //从链表中找到对应的id
            empLinkedListArray[No].delete(id);

        }

        //编写一个散列函数,使用一个简单的取模法
        public int hashFun(int id) {
            return id % size;
        }


    }


}
