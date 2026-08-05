///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Human {

	private class Heart {
		String stream;

		Heart(String s) {
			stream = s;
		}
	}

	public class FourthHand {
		private String name;

		FourthHand(String name) {
			this.name = name;
		}

		public void takeYourHeart() {
			System.out.println("偷偷偷走你的心");
			heart = new Heart("在你的心里(LIU)下一滴泪 By %s".formatted(name));
		}
	}

	private Heart heart = new Heart("寻找白晶晶");

	@Override
	public String toString() {
		return heart.stream;
	}
}

void main(String... args) {
	Human zhiZunBao = new Human();
	System.out.println(zhiZunBao);
	Human.FourthHand fourthHand = zhiZunBao.new FourthHand("紫霞");
	fourthHand.takeYourHeart();
	System.out.println(zhiZunBao);
}
