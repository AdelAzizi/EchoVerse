package phase3_team;

public class TeamMember {

    String name;                 // نام عضو
    TeamMember firstChild;       // اولین زیردست
    TeamMember nextSibling;      // عضو هم‌سطح بعدی

    public TeamMember(String name) {
        this.name = name;
        this.firstChild = null;
        this.nextSibling = null;
    }
}
