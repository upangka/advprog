import java.util.Arrays;
import java.util.List;

public class Sort {
	public static int findSmallest(String[] inputs, int start) {
		int smallestSoFarIndex = start;
		for (int i = start; i < inputs.length; i++) {
			int ret = inputs[i].compareTo(inputs[smallestSoFarIndex]);
			if (ret < 0) {
				smallestSoFarIndex = i;
			}
		}
		return smallestSoFarIndex;
	}

	public static void swap(String[] inputs, int i, int j) {
		List<String> temp = List.of(inputs[i], inputs[j]);
		inputs[i] = temp.get(1);
		inputs[j] = temp.get(0);
	}

	public static void sort(String[] inputs) {
		sort(inputs, 0);
	}

	private static void sort(String[] inputs, int start) {
		if (start >= inputs.length)
			return;
		int smallestIndex = findSmallest(inputs, start);
		swap(inputs, start, smallestIndex);
		sort(inputs, start + 1);
	}

	public static void main(String[] args) {
		String[] inputs = { "grape", "apple", "honeydew", "date", "elderberry", "banana", "fig", "cherry" };
		sort(inputs);
		System.out.println(Arrays.toString(inputs));
	}
}
