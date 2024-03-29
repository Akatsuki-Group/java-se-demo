package org.example.jvm;

public class JavaCountUppercase {
    static final int ITERATIONS = java.lang.Math.max(Integer.getInteger("iterations", 1), 1);
    public static void main(String[] args) {
        String sentence = String.join(" ", args);
        for (int iter = 0; iter < ITERATIONS; iter++) {
            if (ITERATIONS != 1) System.out.println("-- iteration " + (iter + 1) + " --");
            long total = 0, start = System.currentTimeMillis(), last = start;

            for (int i = 1; i < 100_000_000; i++) {
                total += sentence.chars().filter(Character::isUpperCase).count();
                if (i % 10_000_000 == 0) {
                    long now = System.currentTimeMillis();
                    System.out.printf("%d (%d ms)%n", i / 10_000_000, now - last);
                    last = now;
                }
            }
            System.out.printf("total: %d (%d ms)%n", total, System.currentTimeMillis() - start);
        }
    }
}
