package ysaak.apimanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "API", uniqueConstraints = {
        @UniqueConstraint(columnNames = "API_CODE"),
        @UniqueConstraint(columnNames = "API_NAME")
})
public class Api {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "API_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "API_CODE", nullable = false, unique = true, length = 30)
    @NotNull
    @Size(min = 5, max = 30)
    private String code;

    @Column(name = "API_NAME", nullable = false, unique = true, length = 50)
    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    @Column(name = "API_DESCRIPTION")
    @Size(max = 255)
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "api", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApiVersion> versions = new HashSet<>(0);

    public Api() {
    }

    public Api(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ApiVersion> getVersions() {
        return versions;
    }

    public void setVersions(Set<ApiVersion> versions) {
        this.versions = versions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Api api = (Api) o;
        return Objects.equals(code, api.code) &&
                Objects.equals(name, api.name) &&
                Objects.equals(description, api.description) &&
                Objects.equals(versions, api.versions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description, versions);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Api.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("code='" + code + "'")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("versions=" + versions)
                .toString();
    }
}
