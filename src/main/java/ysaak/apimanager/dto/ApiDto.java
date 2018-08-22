package ysaak.apimanager.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class ApiDto {
    private String code;

    private String name;

    private String description;

    private Set<ApiVersionDto> versions;

    public ApiDto() {
    }

    public ApiDto(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setVersions(Set<ApiVersionDto> versions) {
        this.versions = versions;
    }

    public Set<ApiVersionDto> getVersions() {
        return versions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiDto apiDto = (ApiDto) o;
        return Objects.equals(code, apiDto.code) &&
                Objects.equals(name, apiDto.name) &&
                Objects.equals(description, apiDto.description) &&
                Objects.equals(versions, apiDto.versions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description, versions);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiDto.class.getSimpleName() + "[", "]")
                .add("code='" + code + "'")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("versions=" + versions)
                .toString();
    }
}
