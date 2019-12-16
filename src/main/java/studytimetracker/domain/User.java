
package studytimetracker.domain;

import java.util.Objects;

/**
 * Käyttäjää kuvaava luokka
 */
public class User {
    private String username;
    
    /**
     * Luokan konstruktori
     * @param username Käyttjän nimi
     */
    public User(String username) {
        this.username = username;
    }
    
    /**
     * Palauttaa olion nimen
     * @return username
     */
    
    public String getName() {
        return this.username;
    }
    
    /**
     * hashcoden laskeva metodi
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.username);
        return hash;
    }
    
    /**
     * Määrittelee equals arvon
     * @param obj
     * @return true|false sen mukaan onko olio sama
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
    
}
