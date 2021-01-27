public class MyQueue<E> {

    public class Node{
        public Node parent;
        public Node child;
        public E value;

        private Node(E value){
            this.value = value;
        }
    }
    public Node head;
    public Node tail;

    public MyQueue() {
        this.head = null;
        this.tail = null;
    }

    private void updateHead(Node newHead){
        this.head = newHead;
    }
    private void updateTail(Node newTail){
        this.tail = newTail;
    }

    public void addEnd(E element){
        Node newNode = new Node(element);
        if (this.tail != null){
            this.tail.child = newNode;
            newNode.parent = this.tail;
        }else{
            updateHead(newNode);
        }
        updateTail(newNode);
    }

    public void addBeginning(E element){
        Node newNode = new Node(element);
        if (this.head != null){
            this.head.parent = newNode;
            newNode.child = this.head;
            updateHead(newNode);
        }else{
            updateHead(newNode);
            updateTail(newNode);
        }
    }

    public Node pop(){
        if(this.head != null) {
            Node removeing = this.head;
            updateHead(this.head.child);
            if(this.head != null){
                this.head.parent = null;
            }
            if(this.tail == removeing){
                this.tail= null;
            }
            return removeing;
        }
        return null;
    }

    public boolean isEmpty(){
        return this.head == null && this.tail == null;
    }


}
