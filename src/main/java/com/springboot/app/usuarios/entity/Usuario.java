package com.springboot.app.usuarios.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

/*  Serializable
    Para que un programa java pueda convertir un objeto en un montón de bytes y pueda luego recuperarlo, el objeto necesita ser Serializable.
    Al poder convertir el objeto a bytes, ese objeto se puede enviar a través de red, guardarlo en un fichero, y después reconstruirlo al otra
    lado de la red, leerlo del fichero,....
*/

@Getter
@Setter
@Entity(name = "usuarios") //indica que es una clase de persistencia
public class Usuario implements Serializable {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 60)
    private String password;
    private Boolean enabled;
    private String nombre;
    private String apellido;

    @Column(unique = true, length = 100)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})} //Constraint campo unico es decir un usuario no puede tener un rol repetido
    )
    private List<Rol> roles;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    	
    /*
        Cuando pasamos objetos Serializable de un lado a otro tenemos un pequeño problema. Si la clase que queremos
        pasar es objeto_serializable, lo normal, salvo que usemos Carga dinámica de clases, es que en ambos
        lados (el que envía y el que recibe la clase), tengan su propia copia del fichero objeto_serializable.class.
        Es posible que en distintas versiones de nuestro programa la clase objeto_serializable cambie, de forma que es posible que
        un lado tenga una versión más antigua que en el otro lado. Si sucede esto, la reconstrucción de la clase en el lado que recibe es imposible.
    */

    // Para evitar este problema, se aconseja que la clase objeto_serializable tenga un atributo privado de esta forma
	private static final long serialVersionUID = -4979913564224250614L;
    /*de forma que el numerito que ponemos al final debe ser distinto para cada versión de compilado que tengamos. 
    De esta forma, java es capaz de detectar rápidamente que las versiones de objeto_serializable.class en ambos lados son distintas.*/
}
