package sortingPhonesEclipse;

import java.util.ArrayList;
import java.util.Collections;

class PhoneList {
	ArrayList<Phone> phones;

	PhoneList() {

		phones = new ArrayList<Phone>();
	}

	public void addPhone(Phone a) {

		phones.add(a);
	}

	public int getSize() {
		return phones.size();
	}

	public void printPhoneList() {

		for (Phone phone : phones) {

			System.out.println(phone.toString());
		}
	}

	public void sortArray() {

		Collections.sort(phones);
	}

	public PhoneList getCostOver(int price) {

		PhoneList z = new PhoneList();
		for (Phone phone : phones) {

			if (phone.getApprox_price_EUR() > price) {
				z.addPhone(phone);
			}
		}
		return z;
	}

	public Phone findPhone(String brand, String model) {

		for (Phone phone : phones) {

			if (phone.getBrand().equals(brand) && phone.getModel().equals(model)) {

				return phone;

			}
		}

		return null;
	}

	public PhoneList findPhonesInPriceRange(int lower, int higher) {

		PhoneList phoneRange = new PhoneList();
		for (Phone phone : phones) {

			if (phone.getApprox_price_EUR() >= lower && phone.getApprox_price_EUR() <= higher) {
				phoneRange.addPhone(phone);
			}
		}
		return phoneRange;
	}
}
