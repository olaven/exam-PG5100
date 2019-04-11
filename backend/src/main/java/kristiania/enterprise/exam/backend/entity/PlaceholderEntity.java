package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class PlaceholderEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @NotNull
    @Min(0)
    @Max(20)
    private Integer counter;

    @NotNull
    private Integer a;

    @NotNull
    private Integer b;

    @NotNull
    private Integer c;

    public PlaceholderEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceholderEntity)) return false;
        PlaceholderEntity entity = (PlaceholderEntity) o;
        return Objects.equals(getId(), entity.getId()) &&
                Objects.equals(getCounter(), entity.getCounter()) &&
                Objects.equals(getA(), entity.getA()) &&
                Objects.equals(getB(), entity.getB()) &&
                Objects.equals(getC(), entity.getC());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCounter(), getA(), getB(), getC());
    }
}
