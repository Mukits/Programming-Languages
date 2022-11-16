package sortingPhonesEclipse;

import java.util.Scanner;

public class PhoneMain {

	public static void main(String[] args) {

		String path = "PUT YOUTR DATA PATH"; //INSERT HERE YOUR DATA.CSV FILE PATH NOTE USE "/" FOR WINDOWS
		Scanner input = InputReader.getScanner(path);

		PhoneList phoneA = new PhoneList();

		input.nextLine();

		while (input.hasNext()) {

			String line = input.nextLine();
			String[] splitLine = line.split(",");
			Phone newPhone = new Phone(splitLine[0]);

			newPhone.setModel(splitLine[1]);
			newPhone.setAnnounced(splitLine[8]);
			newPhone.setWeight_g(Float.parseFloat(splitLine[11]));
			newPhone.setInternal_memory(splitLine[21]);
			newPhone.setApprox_price_EUR(Integer.parseInt(splitLine[36]));
			// println(newPhone.toString());
			// println(line);
			phoneA.addPhone(newPhone);

			// phoneA.sort();
			// phoneA = phoneA.getCostOver(600);
			// Phone foundPhone = phoneA.findPhone("Huawei", "Mate 9 Porsche Design");
			// phoneA.printPhoneList();

			// println(phoneA.getSize());
			// println(foundPhone.toString());

			// Phone temp = phoneS.findPhone("Apple","iPhone 7 Plus");
			// print(temp.toString());
			// println(phoneS.getSize());
			// phoneA = phoneS.getCostOver(0);

			// phoneS.printPhoneMap();
			// phoneA.sort();
			// phoneA.printPhoneList();

		}

		Scanner in = new Scanner(System.in);
		System.out.println("please input upper bound value");
		int upperBound = in.nextInt();
		System.out.println("please input lower bound value" + "");
		int lowerBound = in.nextInt();

		phoneA = phoneA.findPhonesInPriceRange(lowerBound, upperBound);
		phoneA.sortArray();
		phoneA.printPhoneList();
	}

}
