import java.io.*;

class AVLTree<K extends Comparable<K>, V> {
    class Node {
        K key;
        V value;
        Node parent, left, right;

        Node(K k, V v) { key = k; value = v; }

        // `this` 노드의 자식노드로 `new_node`를 삽입한다. `this` 노드의
        // `left`와 `right`가 모두 이미 할당되어있다면, `left`와 `right`의
        // 자식들에 대해 재귀적으로 `insert()`를 실행하게된다.
        //
        // 만약 `this` 노드와 `new_node`의 키가 같을경우 실패하여 `false`를
        // 반환한다. 성공적으로 삽입에 성공한경우 `true`를 반환한다.
        boolean insert(Node new_node) {
            int compare = new_node.key.compareTo(this.key);

            if (compare == 0) { return false; }
            if (compare < 0) {
                // new_node < this
                if (left != null) { return left.insert(new_node); }
                this.left = new_node;
            } else {
                // new_node > this
                if (right != null) { return right.insert(new_node); }
                this.right = new_node;
            }

            new_node.parent = this;
            return true;
        }

        @Override
        public String toString() {
            String ret = key.toString();
            if (left != null) { ret = left.toString() + ' ' + ret; }
            if (right != null) { ret += ' ' + right.toString(); }
            return ret;
        }

        // BST의 부모자식 포인터가 서로 상대방을 올바르게 가리키고있는지, BST의
        // 노드들이 바이너리서치트리의 Invariant를 제대로 만족하고있는지
        // 검사한다.
        //
        // ```java
        // root.validate(null);
        //
        // node.validate(p);
        // ```
        //
        // 위와 같이 호출할경우, `node`의 부모가 `p`가 맞는지 여부를 검사한 뒤,
        // node를 루트로 하는 BST가 올바른 트리인지 검사하게된다.
        //
        // TODO: AVLTree invariant 만족 여부 검증
        boolean validate(Node parent) {
            return this.parent == parent &&
                (left  == null || (left .validate(this) && left .key.compareTo(this.key) < 0)) &&
                (right == null || (right.validate(this) && right.key.compareTo(this.key) > 0));
        }
    }

    Node root = null;

    boolean insert(K key, V value) {
        // New node which will be inserted
        Node n = new Node(key, value);

        // Check if initial insertion
        if (root == null) { root = n; return true; }
        return root.insert(n);

        // TODO: Balancing
    }

    @Override
    public String toString() { return root == null ? "" : root.toString(); }

    // 올바른 바이너리서치트리인지 검사한다.
    //
    // TODO: AVLTree invariant 만족여부 확인하기
    boolean validate() { return root == null || root.validate(null); }
}

public class Matching {
    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String input = br.readLine();
                if (input == null) { break; }
                if (input.compareTo("QUIT") == 0) { break; }

                command(input);
            } catch (IOException e) {
                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
            }
        }
    }

    // TODO: Implement
    static AVLTree<Integer, Void> map = new AVLTree<Integer, Void>();
    private static void command(String line) {
        int input = Integer.parseInt(line);
        boolean ret = map.insert(input, null);
        if (!ret) { return; }

        // TODO: Remove debug codes
        if (!map.validate()) { System.out.println("\u001B[33mInvalid BST warning\u001B[0m"); }
        System.out.printf("Inserted %d, [ %s ]\n", input, map);
    }
}