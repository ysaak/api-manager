package ysaak.apimanager.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class ApiVersionDto {
    private String version;

    private String url;

    public ApiVersionDto() {
    }

    public ApiVersionDto(String version, String url) {
        this.version = version;
        this.url = url;
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
        ApiVersionDto that = (ApiVersionDto) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, url);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiVersionDto.class.getSimpleName() + "[", "]")
                .add("version='" + version + "'")
                .add("url='" + url + "'")
                .toString();
    }
}
