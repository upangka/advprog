///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS tools.jackson.core:jackson-databind:3.2.1

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

static class Club {
	private Map<Student, Country> countryMap;

	public Club() {
		this.countryMap = new HashMap<>();
	}

	public void addStudent(Student stu, Country country) {
		this.countryMap.put(stu, country);
	}
}

static record Country(String name) {

	@Override
	public final String toString() {
		return this.name;
	}
}

static record Student(String name) {
}

static Map<Country, Integer> countByCountry(List<Club> allClubs) {
	var counts = new HashMap<Country, Integer>();
	var uniqueStudents = new HashSet<Student>();
	for (Club club : allClubs) {
		for (Map.Entry<Student, Country> entry : club.countryMap.entrySet()) {

			Student stu = entry.getKey();
			Country country = entry.getValue();

			if (!uniqueStudents.contains(stu)) {
				int count = counts.computeIfAbsent(country, k -> 0);
				counts.put(country, count + 1);
				uniqueStudents.add(stu);
			}
		}
	}
	return counts;
}

static List<Club> createClubs() {
	// 创建国家
	Country scotland = new Country("Scotland");
	Country brazil = new Country("Brazil");

	// 创建学生
	Student aditya = new Student("Aditya");
	Student natalia = new Student("Natalia");
	Student rushil = new Student("Rushil");

	// 俱乐部1：国际象棋俱乐部
	Club chessClub = new Club();
	chessClub.addStudent(aditya, scotland);
	chessClub.addStudent(natalia, brazil);
	chessClub.addStudent(rushil, scotland);

	// 俱乐部2：攀岩俱乐部
	Club climbingClub = new Club();
	climbingClub.addStudent(natalia, brazil);

	return List.of(chessClub, climbingClub);
}

void main(String... args) {
	List<Club> clubs = createClubs();
	var ret = countByCountry(clubs);

	JsonMapper jsonMapper = JsonMapper.builder()
		.enable(SerializationFeature.INDENT_OUTPUT)
		.build();

	System.out.println(jsonMapper.writeValueAsString(ret));
}

/**output
{
  "Brazil" : 1,
  "Scotland" : 2
}
*/