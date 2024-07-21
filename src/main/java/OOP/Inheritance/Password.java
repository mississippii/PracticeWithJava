package OOP.Inheritance;

public final class Password {
    private char[] hash;

    public Password(char[] hash) {
        this.hash= hash;
    }
}

/*NB: This class is final class so this couldn't be inherited*/
