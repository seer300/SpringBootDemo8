package com.dudu;
import org.xm.Similarity;

public class test {
    public static void main(String[] args) {
        String sentence1 = "车灯不亮转向灯失灵。";
        String sentence2 = "车灯不亮";

        double morphoSimilarityResult = Similarity.morphoSimilarity(sentence1, sentence2);
//        double editDistanceResult = Similarity.editDistanceSimilarity(sentence1, sentence2);
//        double standEditDistanceResult = Similarity.standardEditDistanceSimilarity(sentence1,sentence2);
//        double gregeorEditDistanceResult = Similarity.gregorEditDistanceSimilarity(sentence1,sentence2);

        System.out.println(sentence1 + " vs " + sentence2 + " 词形词序句子相似度值：" + morphoSimilarityResult);
//        System.out.println(sentence1 + " vs " + sentence2 + " 优化的编辑距离句子相似度值：" + editDistanceResult);
//        System.out.println(sentence1 + " vs " + sentence2 + " 标准编辑距离句子相似度值：" + standEditDistanceResult);
//        System.out.println(sentence1 + " vs " + sentence2 + " gregeor编辑距离句子相似度值：" + gregeorEditDistanceResult);

    }
}
