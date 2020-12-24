package model;

public enum MovieType {
    MOVIE_2D {
        @Override
        public String toString(){
            return  "2D";
        }
    },
    MOVIE_3D {
        @Override
        public String toString(){
            return  "3D";
        }
    },
}
