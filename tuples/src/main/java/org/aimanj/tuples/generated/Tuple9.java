package org.aimanj.tuples.generated;

import org.aimanj.tuples.Tuple;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.aimanj.codegen.TupleGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 */
public final class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> implements Tuple {
    private static final int SIZE = 9;

    private final T1 value1;

    private final T2 value2;

    private final T3 value3;

    private final T4 value4;

    private final T5 value5;

    private final T6 value6;

    private final T7 value7;

    private final T8 value8;

    private final T9 value9;

    public Tuple9(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
        this.value6 = value6;
        this.value7 = value7;
        this.value8 = value8;
        this.value9 = value9;
    }

    public T1 getValue1() {
        return value1;
    }

    public T2 getValue2() {
        return value2;
    }

    public T3 getValue3() {
        return value3;
    }

    public T4 getValue4() {
        return value4;
    }

    public T5 getValue5() {
        return value5;
    }

    public T6 getValue6() {
        return value6;
    }

    public T7 getValue7() {
        return value7;
    }

    public T8 getValue8() {
        return value8;
    }

    public T9 getValue9() {
        return value9;
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ?> tuple9 = (Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ?>) o;
        if (value1 != null ? !value1.equals(tuple9.value1) : tuple9.value1 != null) {
            return false;
        }
        if (value2 != null ? !value2.equals(tuple9.value2) : tuple9.value2 != null) {
            return false;
        }
        if (value3 != null ? !value3.equals(tuple9.value3) : tuple9.value3 != null) {
            return false;
        }
        if (value4 != null ? !value4.equals(tuple9.value4) : tuple9.value4 != null) {
            return false;
        }
        if (value5 != null ? !value5.equals(tuple9.value5) : tuple9.value5 != null) {
            return false;
        }
        if (value6 != null ? !value6.equals(tuple9.value6) : tuple9.value6 != null) {
            return false;
        }
        if (value7 != null ? !value7.equals(tuple9.value7) : tuple9.value7 != null) {
            return false;
        }
        if (value8 != null ? !value8.equals(tuple9.value8) : tuple9.value8 != null) {
            return false;
        }
        return value9 != null ? value9.equals(tuple9.value9) : tuple9.value9 == null;
    }

    @Override
    public int hashCode() {
        int result = value1.hashCode();
        result = 31 * result + (value2 != null ? value2.hashCode() : 0);
        result = 31 * result + (value3 != null ? value3.hashCode() : 0);
        result = 31 * result + (value4 != null ? value4.hashCode() : 0);
        result = 31 * result + (value5 != null ? value5.hashCode() : 0);
        result = 31 * result + (value6 != null ? value6.hashCode() : 0);
        result = 31 * result + (value7 != null ? value7.hashCode() : 0);
        result = 31 * result + (value8 != null ? value8.hashCode() : 0);
        result = 31 * result + (value9 != null ? value9.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tuple9{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                ", value3=" + value3 +
                ", value4=" + value4 +
                ", value5=" + value5 +
                ", value6=" + value6 +
                ", value7=" + value7 +
                ", value8=" + value8 +
                ", value9=" + value9 +
                "}";
    }
}
