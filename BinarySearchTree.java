import com.sun.source.tree.BinaryTree;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BinarySearchTree {
    TreeNode root;
    private int size, next;
    private Student[] data;

    //no-arg constructor to initialize root
    public BinarySearchTree() {
        root = null;
        next = 0;
        size = 100;
        data = new Student[size];
    }

    public boolean insert(Student newNode) {
        TreeNodeWrapper p = new TreeNodeWrapper();
        TreeNodeWrapper c = new TreeNodeWrapper();
        TreeNode n = new TreeNode();
        if (n == null)                              //out of memory
            return false;
        else {                                      //insert the node
            n.node = newNode.deepCopy();         //fill in the TreeNode's field
            n.lc = null;
            n.rc = null;
            if (root == null)                      //tree is empty
                root = n;
            else {                                      /////////////////////////////////////////
                findNode(newNode.getStudentID(), p, c);     //find the node's parent
                if (newNode.getStudentID().compareTo(p.get().node.getStudentID()) < 0)
                    p.get().lc = n;               //insert the new node as a left child
                else p.get().rc = n;              //insert the new node as a right child
            }

            //to put the new students in an array to use in showAll()
            if (next >= size) //if the structure if full
                return false;
            data[next] = newNode.deepCopy(); //store a deep copy of the student's node


            if (data[next] == null) //if there's insufficient System memory
                return false;
            next++; //increment next to prepare for the next insert
            return true;
        }
    }   //end of insert method

    public Student fetch(String targetKey) {
        boolean found;
        TreeNodeWrapper p = new TreeNodeWrapper();
        TreeNodeWrapper c = new TreeNodeWrapper();
        found = findNode(targetKey, p, c);        //locate the node
        if (found)
            return c.get().node.deepCopy();
        else return null;
    }   //end of fetch method

    public boolean delete(String targetKey) {
        boolean found;
        TreeNodeWrapper p = new TreeNodeWrapper();
        TreeNodeWrapper c = new TreeNodeWrapper();
        TreeNode largest;
        TreeNode nextLargest;
        found = findNode(targetKey, p, c);
        if (found == false) return false;           //if node not found
        else {                                      //identify the case number
            if (c.get().lc == null && c.get().rc == null)      //case 1: deleted node has no children
            {
                if (p.get().lc == c.get())         //deleted node is a left child
                    p.get().lc = null;
                else p.get().rc = null;           //deleted node is a right child
            }   //end case 1
            else if (c.get().lc == null || c.get().rc == null)   //case 2: 1 child
            {
                if (p.get().lc == c.get())         //deleted node is left child
                {
                    if (c.get().lc != null)      //deleted node has left child
                        p.get().lc = c.get().lc;
                    else p.get().lc = c.get().rc;
                } else {
                    if (c.get().lc != null)       //deleted node is a left child
                        p.get().rc = c.get().lc;  //deleted node has a left child
                    else p.get().rc = c.get().rc;
                }
            }   //end of case 2
            else    //case 3: deleted node has two children
            {
                nextLargest = c.get().lc;
                largest = nextLargest.rc;
                if (largest != null)                  //left child has a right subtree
                {
                    while (largest.rc != null)       //move down right subtree
                    {
                        nextLargest = largest;
                        largest = largest.rc;
                    }
                    c.get().node = largest.node;      //overwrite deleted node
                    nextLargest.rc = largest.lc;      //save the left subtree
                } else    //left child does not have a right subtree
                {
                    nextLargest.rc = c.get().rc;      //save the right subtree
                    if (p.get().lc == c.get())         //if the deleted node is a left child
                        p.get().lc = nextLargest;     //jump around deleted node
                    else                            //deleted node is right child
                        p.get().rc = nextLargest;     //jump around deleted node
                }
            }   //end of case 3
            return true;
        }
    }   //end of delete method

    public boolean update(String targetKey, Student newListing) {
        if (delete(targetKey) == false)
            return false;
        else if (insert(newListing) == false)
            return false;
        return true;
    }   //end of update method

    public void showAll() {
        for (int i = 0; i < size; i++) {
            if (data[i] != null) System.out.println(data[i].toString());
        }
    }

    //Print the list of students in one class
    public void classList(String className) {
        for (int i = 0; i < size; i++)             //For every student
        {
            Student oneStudent = data[i];       //data[i] is a student
            if (oneStudent != null)              //for the NullPointerException
            {
                ArrayList<Aclass> list = oneStudent.getTranscript();
                for (int j = 0; j < list.size(); j++)  //iterate through all the classes
                {
                    if (list.get(j).getClasName().equalsIgnoreCase(className)) {
                        System.out.println(oneStudent.getStudentID() + " - " + oneStudent.getName());
                    }
                }
            }
        }
    }

    private boolean findNode(String targetKey, TreeNodeWrapper parent, TreeNodeWrapper child) {
        parent.set(root);
        child.set(root);
        if (root == null)      //if the tree is empty
            return true;
        while (child.get() != null) {
            if (child.get().node.compareTo(targetKey) == 0)    //if the node is found
                return true;
            else {
                parent.set(child.get());
                if (targetKey.compareTo(child.get().node.getStudentID()) < 0)    ///////////////////////////
                    child.set(child.get().lc);
                else child.set(child.get().rc);
            }
        }   //end of while loop
        return false;
    }   //end of findNode method

    public class TreeNode {
        private Student node;
        private TreeNode lc;
        private TreeNode rc;

        public TreeNode() {
        }
    }   //end of class TreeNode

    public class TreeNodeWrapper {
        TreeNode treeRef = null;

        public TreeNodeWrapper() {
        }

        public TreeNode get() {
            return treeRef;
        }

        public void set(TreeNode t) {
            treeRef = t;
        }
    }   //end of TreeNodeWrapper
}   //end of class BinarySearchTree
