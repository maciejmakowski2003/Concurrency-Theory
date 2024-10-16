import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var nodesNumber = 10;
        var nodes = new ArrayList<Node>();

        var inputBuffer = new Buffer();
        var outputBuffer = new Buffer();

        for (int i = 0; i < nodesNumber; i++) {
            if (i == 0) {
                nodes.add(new Source(outputBuffer));
            } else if (i == nodesNumber - 1) {
                nodes.add(new Destination(inputBuffer));
            } else {
                inputBuffer = outputBuffer;
                outputBuffer = new Buffer();
                nodes.add(new Node(inputBuffer, outputBuffer));
            }
        }

        for (Node node: nodes) {
            node.start();
        }
    }
}