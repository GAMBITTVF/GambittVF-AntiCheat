package model;

public class Jogador {
    private int id;
    private String nickname;
    private boolean banido;

    public Jogador(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.banido = false;
    }

    public Jogador(int id, String nickname, boolean banido) {
        this.id = id;
        this.nickname = nickname;
        this.banido = banido;
    }

    public int getId() { return id; }
    public String getNickname() { return nickname; }
    public boolean isBanido() { return banido; }
    public void setBanido(boolean banido) { this.banido = banido; }
}
