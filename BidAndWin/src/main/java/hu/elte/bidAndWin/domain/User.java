package hu.elte.bidAndWin.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
//    @OneToOne(targetEntity = Bidder.class, mappedBy = "user")
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY, optional = false)
//    @JsonIgnore
    private Bidder bidder;
    
    @OneToOne(targetEntity = Seller.class, mappedBy = "user")
//	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, 
//            fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Seller seller;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    
    @Column(nullable=false, unique = true)
    private String username;
    
    @Column(nullable=false)
    private String password;
    
    @Column(nullable=false, unique = true)
    private String email;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    
    public enum Role {
        GUEST, USER, ADMIN
    }
}