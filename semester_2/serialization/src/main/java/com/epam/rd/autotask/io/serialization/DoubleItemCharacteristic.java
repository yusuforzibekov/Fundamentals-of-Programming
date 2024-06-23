package com.epam.rd.autotask.io.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class DoubleItemCharacteristic extends ItemCharacteristic implements Serializable {
    private static final long serialVersionUID = 1L;
    protected double value;

    public DoubleItemCharacteristic(Long id, String name, String type, double value) {
        super(id, name, type);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeLong(id);
        oos.writeUTF(name);
        oos.writeUTF(type);
        oos.writeDouble(value);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        id = ois.readLong();
        name = ois.readUTF();
        type = ois.readUTF();
        value = ois.readDouble();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DoubleItemCharacteristic))
            return false;
        if (!super.equals(o))
            return false;
        DoubleItemCharacteristic that = (DoubleItemCharacteristic) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
