package org.example;

public enum MathFunction {
    ADD {
        @Override
        public String toString(){
            return "ADD";
        }
    },
    SUBTRACT {
        @Override
        public String toString(){
            return "SUBTRACT";
        }
    },
    MULTIPLY{
        @Override
        public String toString(){
            return "MULTIPLY";
        }
    },
    DIVIDE {
        @Override
        public String toString(){
            return "DIVIDE";
        }
    }
}
