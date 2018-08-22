package ysaak.apimanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "API_VERSION")
public class ApiVersion implements Comparable<ApiVersion> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APVE_ID", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APVE_API_ID", nullable = false)
    private Api api;

    @Column(name = "APVE_VERSION", nullable = false, unique = true)
    private String version;

    @Column(name = "APVE_URL", nullable = false)
    private String url;

    public ApiVersion() {
    }

    public ApiVersion(String version, String url) {
        this.version = version;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiVersion that = (ApiVersion) o;
        return Objects.equals(api.getCode(), that.api.getCode()) &&
                Objects.equals(version, that.version) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(api.getCode(), version, url);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiVersion.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("version='" + version + "'")
                .add("url='" + url + "'")
                .toString();
    }

    @Override
    public int compareTo(ApiVersion o) {
        String[] levels1 = version.split("\\.");
        String[] levels2 = o.getVersion().split("\\.");

        int length = Math.max(levels1.length, levels2.length);
        for (int i = 0; i < length; i++){
            Integer v1 = i < levels1.length ? Integer.parseInt(levels1[i]) : 0;
            Integer v2 = i < levels2.length ? Integer.parseInt(levels2[i]) : 0;
            int compare = v1.compareTo(v2);
            if (compare != 0){
                return compare;
            }
        }

        return 0;
    }
}
