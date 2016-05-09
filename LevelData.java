package com.ancientlore.squash;

abstract class LevelData {

    private LevelData(){}

    static int[][] getLevel(String level){
        switch (level){
            default:return null;
            case "test":
                return new int[][]
                        {{0,0,0,0,0,0,0,0,0,0},
                                {1,1,1,1,1,1,1,1,1,1},
                                {0,0,0,0,0,0,0,0,0,0}};
            case "0":
                return new int[][]
                       {{1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1}};

            case "1":
                return new int[][]
                       {{0,0,0,0,1,1,0,0,0,0},
                        {0,0,0,1,2,2,1,0,0,0},
                        {0,0,1,2,3,3,2,1,0,0},
                        {0,1,2,3,4,4,3,2,1,0},
                        {1,2,3,4,0,0,4,3,2,1},
                        {1,2,3,4,0,0,4,3,2,1},
                        {0,1,2,3,4,4,3,2,1,0},
                        {0,0,1,2,3,3,2,1,0,0},
                        {0,0,0,1,2,2,1,0,0,0},
                        {0,0,0,0,1,1,0,0,0,0}};
            case "2":
                return new int[][]
                        {{1,1,0,0,0,0,0,0,1,1},
                        {1,1,1,0,0,0,0,1,1,1},
                        {0,1,1,1,0,0,1,1,1,0},
                        {0,0,1,1,0,0,1,1,0,0},
                        {0,0,0,1,1,1,1,0,0,0},
                        {0,0,0,1,1,1,1,0,0,0},
                        {0,0,1,1,1,1,1,1,0,0},
                        {0,1,1,1,0,0,1,1,1,0},
                        {1,1,1,0,0,0,0,1,1,1},
                        {1,1,0,0,0,0,0,0,1,1}};
        }
    }
}
