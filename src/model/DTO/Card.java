package model.DTO;

public enum Card {
    CRVENI
            {
                @Override
                public String toString() {
                    return "CRVENI";
                }
            },
    ZUTI
            {
                @Override
                public String toString() {
                    return "ZUTI";
                }
            }
}
