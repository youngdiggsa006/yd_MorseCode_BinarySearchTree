/**
 * @author Amaris Young-Diggs
 * @description This project contains two classes; a MorseCharacter class which contains the MorseCode objects
 * (char letter, String morse code) and the MorseCodeTree class. The MorseCodeTree class builds a binary search
 * tree of morse code. It does this by using the bridges BSTElement<K,E> that takes in (String label, String key,
 * MorseCharacter obj). There are additional methods that are there to decode and encode messages and morse code
 * from given inputs. Lastly, there are methods to traverse the tree in numerous ways.
 */
package cmsc256;
import bridges.base.BSTElement;
import bridges.connect.Bridges;
import bridges.validation.RateLimitException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import static java.lang.System.*;

public class MorseCodeTree {
    private static BSTElement<String, MorseCharacter> root;  //static instance var that is the root of the tree

    /**
     * no-arg constructor that calls clear()
     */
    public MorseCodeTree(){ clear(); }

    /**
     * parameterized constructor that takes in a BSTElement<String, MorseCharacter>
     * @param node the root is set to the node
     */
    public MorseCodeTree(BSTElement<String, MorseCharacter> node)
    {
        root = node;
    }

    /**
     * accessor method for the root
     * @return the root of the MorseCodeTree
     */
    public BSTElement<String, MorseCharacter> getRoot() { return root; }

    /**
     * uses a helper method to count the nodes in a bst
     * @return the number of nodes in the tree
     */
    public int getNumberOfNodes() {
        int answer = countNodes(root); //calls the recursive method that counts the nodes
        if(root == null){ return 0; } //if the root is null then return 0
        else return answer;
    }

    /**
     * recursive method that counts the nodes in the tree
     * @param bstRoot the root of the tree
     * @return the number of nodes
     */
    public int countNodes(BSTElement<String,MorseCharacter> bstRoot){
        if(bstRoot == null){ return 0; } //if root is null then return 0
        else return 1 + countNodes(bstRoot.getLeft()) + countNodes(bstRoot.getRight()); //return left & right nodes plus root
    }

    /**
     * calls the recursive method to get the height of tree
     * @return the height of the tree
     */
    public int getHeight() {
        return getHeightHelper(root);

    }

    /**
     * recursive method for getting the height of the tree
     * @param bstRoot the root that is taken in
     * @return the highest height of the tree
     */
    public int getHeightHelper(BSTElement<String,MorseCharacter> bstRoot){
        int rightH = 0, leftH = 0; //initialize two heights to zero (one for L & R)
        if(bstRoot == null) return 0; //if the root is null return 0
        else if(bstRoot.getRight() == null && bstRoot.getLeft() == null) return 0; //if the node is a leaf return 0
        if(bstRoot.getRight() != null){ rightH = getHeightHelper(bstRoot.getRight()); } //if the right child isn't null, recursive call rightSide
        if(bstRoot.getLeft() != null){ leftH = getHeightHelper(bstRoot.getLeft()); } //if left child isn't null, recursive call leftSide
        return Math.max(rightH, leftH) + 1; //compare which side has the heighest height and add 1 to it (account fo root)
    }

    /**
     * checks if the root equals null
     * @return true or false
     */
    public boolean isEmpty(){ return root == null; }

    /**
     * sets the root to null
     */
    public void clear() { root = null; }

    /**
     * this method takes in a Character and is iterated through a linked
     * list to find its morse code representation
     * @param c the character that needs to be encoded
     * @return the morse code representation of the Character
     */
    public static String encode(Character c) {
        if(c == null){ throw new IllegalArgumentException(); } //if c is null then throw IllegalArgument
        BSTElement<String, MorseCharacter> bstNode = root; //declare & initialize a node and set it to the instance var root
        LinkedList<BSTElement<String,MorseCharacter>> myList = new LinkedList<>(); //initialize a linked list of BSTElement type
        myList.add(bstNode.getLeft()); //add the left child to the list
        myList.add(bstNode.getRight()); //add the right child to the list

        while(!myList.isEmpty()){ //while the list isn't empty, go thru each node to see if it matches Character c
            bstNode = myList.pop(); //assign the popped value to the bstNode for it to be evaluated

            if(bstNode.getLeft() != null){ //if the left child isn't null then add that left child to myList
                myList.add(bstNode.getLeft());
            }
            if(bstNode.getRight() != null){ //if the right child isn't null then add that right child to myList
                myList.add(bstNode.getRight());
            }
            if(bstNode.getValue().getLetter() == c){ //if the current bstNode's MorseCharacter value is a match then return it
                return bstNode.getValue().getCode();
            }
        }
        return "";
    }

    /**
     * this method takes in a string of morse code and returns the Character letter that it is
     * it converts the string into an array of char and compares each char in the array,
     * and moves to the left or right child, depending on if the char is a dot or dash
     * @param s  the String being taken in
     * @return the Character letter of the morse code string taken in
     */
    public static Character decode(String s){
        if(s == null){ throw new IllegalArgumentException(); } //if the string is null then throw an illegal argument exception
        BSTElement<String, MorseCharacter> currentBst = root; //initialize a BSTElement to the root (will be used to move around)
        char[] sArray = s.toCharArray(); //convert the string into an array of char type

        for(char c: sArray){
            if(c == '.'){ currentBst = currentBst.getLeft(); } //if the char is '.' then move to the left
            else if(c == '-'){ currentBst = currentBst.getRight(); } //if the char is '-' then move to the right
        }

        String finalString = currentBst.getLabel(); //initialize a string to the current BSTElement's label
        return finalString.charAt(0); //return the string but in the for of a char
    }

    /**
     * this a helper method that removes the null nodes in the traversal methods
     * it works by taking in a string and converting it to a char array.
     * it uses a string builder and appends each char if it is not null
     * @param traversalStr the string that needs to be evaluated
     * @return the traversal string without null spaces
     */
    private static String deleteNull(String traversalStr)  {
        char[] rightOrderArray = traversalStr.toCharArray(); //convert the string to an array of char
        StringBuilder myOrder = new StringBuilder(); //create a string builder object

        for (char c : rightOrderArray) {
            if (c != ' ') { myOrder.append((c)); } //if the char does not equal a space then add it to the string
        }
        return myOrder.toString(); //return the string builder, using the toString method to convert it
    }

    /**
     * this method performs an inorder traversal of the bst
     * it take in the root and evaluates each left subtree, root, then right subtree
     * @param nodeRt the node to be taken in
     * @return the string representation of the tree traversal
     */
    public static String inOrderTraversal(BSTElement<String, MorseCharacter> nodeRt) {
        if(nodeRt ==  null){ //if the root is null then return an empty string
            return "";
        }
        String left = inOrderTraversal(nodeRt.getLeft()); //traverse through the left subtree using the recursive call
        String right = inOrderTraversal(nodeRt.getRight()); //traverse through the right subtree using the recursive call
        if(nodeRt.getValue().equals(new MorseCharacter())){ //this checks for null/empty spaces
            return deleteNull(left + right); //method call to the method that deletes null
        }else{
            return left + nodeRt.getValue().toString() + right; //return the regular string if it contains no null spaces
        }
    }

    /**
     * this traversal takes in the root and uses a helper method to traverse through each
     * level of the tree. a for loop is used to go through different height iterations
     * @param bstNode the node being taken in
     * @return the string representation of the tree traversal
     */
    public static String levelOrderTraversal(BSTElement<String,MorseCharacter> bstNode){
        if(bstNode == null){ //if the node is null then return an empty string
            return "";
        }
        MorseCodeTree mcT = new MorseCodeTree(bstNode); //create a morse code tree object that takes in the node
        int height = mcT.getHeightHelper(bstNode); //call the getHeight method on the tree to get the height of it

        StringBuilder levelHeight = new StringBuilder(); //create a string builder object to be able to add each level

        for(int i = 0; i <= height; i++){ //this for loop goes through each level of the tree using the helper method
            levelHeight.append(levelOrderHelper(bstNode, i));
        }
        if(bstNode.getValue().equals(new MorseCharacter())){ //the node equals an empty morse character then call deleteNull
            return deleteNull(levelHeight.toString());
        }else{
            return levelHeight.toString(); //if it does not contain spaces then return the string
        }
    }

    /**
     * helper method that is called in levelOrderTraversal
     * @param bstNode the node being taken in
     * @param level  the current level of the tree
     * @return the string of letters at the specific tree level
     */
    public static String levelOrderHelper(BSTElement<String,MorseCharacter> bstNode, int level){
        if(bstNode == null){ //if the root is null then return an empty string
            return "";
        }
        if(level == 0){ return bstNode.getLabel(); } //if the level is 0 then return the root
        else{
            return levelOrderHelper(bstNode.getLeft(), level-1) + levelOrderHelper(bstNode.getRight(), level-1); //decrease the height
        }
    }

    /**
     * this method performs a post order traversal of the tree. it takes in a node
     * and traverse through the left subtree and right subtree, then the root
     * @param bstNode the node being taken in
     * @return the string representation of the post order tree traversal
     */
    public static String postOrderTraversal(BSTElement<String, MorseCharacter> bstNode){
        if(bstNode == null){ //if the node is null then return an empty string
            return "";
        }
        String left = postOrderTraversal(bstNode.getLeft()); //obtain the string of letters from the left subtree
        String right = postOrderTraversal(bstNode.getRight()); //obtain the string of letters from the right subtree
        String concatStr = left + right + bstNode.getValue().toString(); //assign an answer to the concat of the left, right, and root

        if(bstNode.getValue().equals(new MorseCharacter())){ //check to see if the node contains empty spaces
            return deleteNull(left + right);
        }else{
            return concatStr; //if it does not contain spaces then return string
        }
    }

    /**
     * this method performs a preorder traversal of the tree. it takes in a node to
     * be traversed through by obtaining the root, then traversing through the left
     * subtree, then the right subtree
     * @param node the node being taken in
     * @return the string representation of the preorder tree traversal
     */
    public static String preOrderTraversal(BSTElement<String, MorseCharacter> node){
        if(node == null){ //if the root is null then return an empty string
            return "";
        }
        String left = preOrderTraversal(node.getLeft()); //assign a string to the letters obtained from traversing thru the left subtree
        String right = preOrderTraversal(node.getRight()); //assign a string to the letters obtained from traversing thru the right subtree
        String concatStr = node.getValue().toString() + left + right; //combine the root, left string, and right string
        if(node.getValue().equals(new MorseCharacter())){ //if there are spaces in the string then call the delete null method
            return deleteNull(left + right);
        }else{
            return concatStr; //if there are no spaces then return the string
        }
    }

    /**
     * this method adds nodes to the bst tree to help build it
     * it first takes in the root and builds it upon the key (morse code)
     * @param bstNode
     */
    public void add(BSTElement<String, MorseCharacter> bstNode){
        String codeCombo = bstNode.getKey(); //assign a string to the morse code of the BSTElement
        if(root == null) { //if the root is null then assign it to the node being taken in
            root = bstNode;
        }
        BSTElement<String, MorseCharacter> temp = root; //create a BSTElement and assign it to the vale of root (will be moved around)
        int i = 0;
        while (i < codeCombo.length()) {
            switch(codeCombo.charAt(i)){
                case '.': //if the character at i is a dot and the left child is null, then a new BSTElement is created for the left child
                    if(temp.getLeft() == null){
                        temp.setLeft(new BSTElement<>("", "", new MorseCharacter()));
                    }
                    temp = temp.getLeft(); //move to the left child
                    break;
                case '-': //if the character at i is a dash and the right child is null, then a new BSTElement is created for the right child
                    if(temp.getRight() == null){
                        temp.setRight(new BSTElement<>("", "", new MorseCharacter()));
                    }
                    temp = temp.getRight(); //move to the right child
                    break;
            }
            i++;
        }
        if(temp.getKey().equals(bstNode.getKey())){ //if the temporary node's morse code equals that of the node we take in then return nothing
            return;
        }
        temp.setKey(codeCombo); //set the morse code of the BSTElement
        temp.setValue(bstNode.getValue()); //set the MorseCharacter of the BSTElement
        temp.setLabel(bstNode.getLabel()); //set the letter of the BSTElement
    }

    /**
     * this method builds a tree using a while loop
     * it takes in a priority queue that is sorted using the MorseCharacter's compareTo method
     * it calls the add method to add BSTElements to the tree as long as the queue isn't empty
     * @param myQ the priority queue being taken in
     * @return the Morse Code binary search tree
     */
    public static MorseCodeTree buildMorseTree(PriorityQueue<MorseCharacter> myQ) {
        MorseCodeTree mT = new MorseCodeTree(); //create a morse code tree (no arg)
        while(!myQ.isEmpty()){ //as long as the queue isn't empty
            MorseCharacter mcObj = myQ.poll(); //assign a Morse Character object to the Morse Character object removed from the queue
            BSTElement<String,MorseCharacter> btsObj = new BSTElement<>(String.valueOf(mcObj.getLetter()), mcObj.getCode(), mcObj); //take in respective params and create BST
            mT.add(btsObj); //add the BSTElement node to the tree
        }
        return mT; //return the sorted Morse Code tree
    }

    /**
     * this method converts the file taken in into a priority queue
     * it does this by creating a scanner that takes in a file, and converting
     * the file contents into Morse Character objects and adding them to the queue
     * @param file that contains the morse code and its representation in the english alphabet
     * @return the priority queue
     * @throws IOException
     */
    public static PriorityQueue<MorseCharacter> convertFileToQueue(File file) throws IOException {
        Scanner input = new Scanner(file); //create a scanner that takes in the file

        PriorityQueue<MorseCharacter> myQ = new PriorityQueue<>(); //create a priority queue of type Morse Character

        myQ.offer(new MorseCharacter(' ', "")); //create an empty node and add it to the queue

        while (input.hasNextLine()) { //while there are still lines to go thru in the file
            String fileLine = input.nextLine(); //converts each line to a string
            String[] splitData = fileLine.split("\\s+"); //splits the data by the white space
            char letter = splitData[0].charAt(0); //assign the Character to the first index
            String expression = splitData[1]; //assigns the second index to the morse code string
            MorseCharacter codeCombo = new MorseCharacter(letter, expression); //create a Morse Character object and take in the letter and expression as parameters
            myQ.offer(codeCombo); //add the Morse Character objects to the queue
        }
        input.close(); //close the scanner
        return myQ; //return the queue at the end
    }

    /**
     * main method to test the traversal methods, encode, and decode
     * @param args
     * @throws RateLimitException
     * @throws IOException
     */
    public static void main(String[] args) throws RateLimitException, IOException {
        File file = new File("codefile.dat");
        PriorityQueue<MorseCharacter> theQueue = new PriorityQueue<>();
        try {
            theQueue = convertFileToQueue(file);
        }catch (FileNotFoundException e){
            out.println("The file does not exist");
        }
        MorseCodeTree mcT = buildMorseTree(theQueue);
        BSTElement<String, MorseCharacter> bstRoot = mcT.getRoot();
        out.println("TESTING OF THE TRAVERSALS");
        out.println("____________________________________________________________________|");
        out.println("Expected InOrder: 5H4SV3IFU?_2ELR!APWJ'16-BDXNC:KYT7Z.GQM;8O9,0" + "     |");
        out.println("Actual Inorder: " + inOrderTraversal(bstRoot) + "       |");
        out.println("____________________________________________________________________|");
        out.println("Expected PreOrder: EISH54V3UF?_2ARL!WPJ1'TNDB6-XKC:YMGZ7.QO8;,90"  + "    |");
        out.println("Actual PreOrder: " + preOrderTraversal(bstRoot) + "      |");
        out.println("____________________________________________________________________|");
        out.println("Expected PostOrder: 54H3VSF?_2UIL!RP'1JWAE-6BXD:CYKN7.ZQG;890,OMT"  + "   |");
        out.println("Actual PostOrder: " + postOrderTraversal(bstRoot) + "     |");
        out.println("____________________________________________________________________|");
        out.println("Expected LevelOrder: ETIANMSURWDKGOHVFLPJBXCYZQ,5432167890?_!'-:.;" +  "  |");
        out.println("Actual LevelOrder: " + levelOrderTraversal(bstRoot) + "    |");
        out.println("____________________________________________________________________|");
        out.println();
        out.println("TESTING OF ENCODE AND DECODE");
        out.println();
        out.println("Encode C" + "\n" + "Expected: -.-." + "\n" + "Actual: " + encode('C') + "\n");
        out.println("Decode .-.-.-" + "\n" + "Expected: !" + "\n" + "Actual: " + decode(".-.-.-"));

        //create the Bridges object, set credentials
        Bridges bridges = new Bridges(6, "ayoungdiggs12", "1614316138913");
        bridges.setTitle("Amaris' Morse Code Binary Search Tree");
        bridges.setDescription("This tree is made from morse code and can be used to encode and decode messages");
        bridges.setDataStructure(bstRoot);
        bridges.visualize();
    }

}
