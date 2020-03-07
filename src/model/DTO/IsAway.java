package model.DTO;

public enum IsAway {
    Jeste {
        @Override
        public String toString() {
            return "Jeste";
        }
    },
    Nije {
        @Override
        public String toString() {
            return "Nije";
        }
    }
}
