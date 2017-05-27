package com.sky.tools.log;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sky.tools.log.Slog.*;
import static java.util.Collections.unmodifiableList;

/**
 * [管理不同Tree，并且负责日志的分发的接口]
 * [detail]
 * Created by Sky on 2017/5/25.
 */
final class Timber {
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    private static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;
    // Both fields guarded by 'FOREST'.
    private static final List<Tree> FOREST = new ArrayList<>();

    public static void v(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        SOULS_TREE.v(tag, t, compoundMsg, normalMsg, args);
    }

    private static void d(String tag, Throwable t, String compoundMsg, String normalMsg, Object[] args) {
        SOULS_TREE.d(tag, t, compoundMsg, normalMsg, args);
    }

    public static void i(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        SOULS_TREE.i(tag, t, compoundMsg, normalMsg, args);
    }

    public static void wtf(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        SOULS_TREE.wtf(tag, t, compoundMsg, normalMsg, args);
    }

    //    public static void v(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.v(prefixTag, compoundMsg, normalMsg, args);
    //    }

    //    public static void d(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.d(prefixTag, compoundMsg, normalMsg, args);
    //    }

    public static void d(String tag, String compoundMsg, @Nullable Object object) {
        SOULS_TREE.d(tag, compoundMsg, object);
    }

    //    public static void i(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.i(prefixTag, compoundMsg, normalMsg, args);
    //    }

    //    public static void w(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.w(prefixTag, compoundMsg, normalMsg, args);
    //    }

    public static void w(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        SOULS_TREE.w(tag, t, compoundMsg, normalMsg, args);
    }

    //    public static void e(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.e(prefixTag, compoundMsg, normalMsg, args);
    //    }

    public static void e(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        SOULS_TREE.e(tag, t, compoundMsg, normalMsg, args);
    }

    //    public static void wtf(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.wtf(prefixTag, compoundMsg, normalMsg, args);
    //    }
    public static void log(int priority, String tag, Throwable t, String compoundMsg, @Nullable String normalMsg,
            @Nullable Object... args) {
        switch (priority) {
            case ERROR:
                e(tag, t, compoundMsg, normalMsg, args);
                break;

            case WARN:
                w(tag, t, compoundMsg, normalMsg, args);
                break;

            case DEBUG:
                d(tag, t, compoundMsg, normalMsg, args);
                break;

            case INFO:
                i(tag, t, compoundMsg, normalMsg, args);
                break;

            case VERBOSE:
                v(tag, t, compoundMsg, normalMsg, args);
                break;

            case ASSERT:
                wtf(tag, t, compoundMsg, normalMsg, args);
                break;

            default:
                break;
        }
    }

    public static void log(int priority, String tag, String compoundMsg, @Nullable Object originalObject) {
        switch (priority) {
            default:
                d(tag, compoundMsg, originalObject);
                break;
        }
    }

    /**
     * A view into Timber's planted trees as a tree itself. This can be used for injecting a logger
     * instance rather than using static methods or to facilitate testing.
     */
    public static Tree asTree() {
        return SOULS_TREE;
    }

    /** Add a new logging tree. */
    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == SOULS_TREE) {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /** Adds new logging trees. */
    public static void plant(Tree... trees) {
        if (trees == null) {
            throw new NullPointerException("trees == null");
        }
        for (Tree tree : trees) {
            if (tree == null) {
                throw new NullPointerException("trees contains null");
            }
            if (tree == SOULS_TREE) {
                throw new IllegalArgumentException("Cannot plant Timber into itself.");
            }
        }
        synchronized (FOREST) {
            Collections.addAll(FOREST, trees);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /** Remove a planted tree. */
    public static void removeTree(Tree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                throw new IllegalArgumentException("Cannot removeTree tree which is not planted: " + tree);
            }
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /** Remove all planted trees. */
    public static void clearTrees() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    /** Return a copy of all planted {@linkplain Tree trees}. */
    public static List<Tree> forest() {
        synchronized (FOREST) {
            return unmodifiableList(new ArrayList<>(FOREST));
        }
    }

    public static int treeCount() {
        synchronized (FOREST) {
            return FOREST.size();
        }
    }

    private static final Tree SOULS_TREE = new Tree() {
        @Override
        public void v(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(tag, t, compoundMsg, normalMsg, args);
            }
        }

        @Override
        public void d(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(tag, t, compoundMsg, normalMsg, args);
            }
        }


        @Override
        public void d(String tag, String compoundMsg, @Nullable Object object) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(tag, compoundMsg, object);
            }
        }

        @Override
        public void i(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(tag, t, compoundMsg, normalMsg, args);
            }
        }

        @Override
        public void w(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(tag, t, compoundMsg, normalMsg, args);
            }
        }

        @Override
        public void e(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(tag, t, compoundMsg, normalMsg, args);
            }
        }

        @Override
        public void wtf(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(tag, t, compoundMsg, normalMsg, args);
            }
        }

        @Override
        protected void log(int priority, String tag, String message) {
            throw new UnsupportedOperationException("do not support this method");
        }
    };
}
