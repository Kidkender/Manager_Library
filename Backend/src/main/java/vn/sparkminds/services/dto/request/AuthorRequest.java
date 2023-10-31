package vn.sparkminds.services.dto.request;

public class AuthorRequest {
    private String name;
    private String bio;
    private String email;
    private String national;

    public AuthorRequest(String name, String bio, String email, String national) {
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.national = national;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }


}
