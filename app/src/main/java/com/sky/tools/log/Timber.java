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
    private static final AbsTree[] TREE_ARRAY_EMPTY = new AbsTree[0];
    private static volatile AbsTree[] forestAsArray = TREE_ARRAY_EMPTY;
    // Both fields guarded by 'FOREST'.
    private static final List<AbsTree> FOREST = new ArrayList<>();

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

    //    public static void v(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.v(defaultTag, compoundMsg, normalMsg, args);
    //    }

    //    public static void d(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.d(defaultTag, compoundMsg, normalMsg, args);
    //    }

    public static void d(String tag, String compoundMsg, @Nullable Object object) {
        SOULS_TREE.d(tag, compoundMsg, object);
    }

    //    public static void i(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.i(defaultTag, compoundMsg, normalMsg, args);
    //    }

    //    public static void w(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.w(defaultTag, compoundMsg, normalMsg, args);
    //    }

    public static void w(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        SOULS_TREE.w(tag, t, compoundMsg, normalMsg, args);
    }

    //    public static void e(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.e(defaultTag, compoundMsg, normalMsg, args);
    //    }

    public static void e(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        SOULS_TREE.e(tag, t, compoundMsg, normalMsg, args);
    }

    //    public static void wtf(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        SOULS_TREE.wtf(defaultTag, compoundMsg, normalMsg, args);
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
    public static AbsTree asTree() {
        return SOULS_TREE;
    }

    /** Add a new logging tree. */
    public static void plant(AbsTree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == SOULS_TREE) {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = FOREST.toArray(new AbsTree[FOREST.size()]);
        }
    }

    /** Adds new logging trees. */
    public static void plant(AbsTree... trees) {
        if (trees == null) {
            throw new NullPointerException("trees == null");
        }
        for (AbsTree tree : trees) {
            if (tree == null) {
                throw new NullPointerException("trees contains null");
            }
            if (tree == SOULS_TREE) {
                throw new IllegalArgumentException("Cannot plant Timber into itself.");
            }
        }
        synchronized (FOREST) {
            Collections.addAll(FOREST, trees);
            forestAsArray = FOREST.toArray(new AbsTree[FOREST.size()]);
        }
    }

    /** Remove a planted tree. */
    public static void removeTree(AbsTree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                throw new IllegalArgumentException("Cannot removeTree tree which is not planted: " + tree);
            }
            forestAsArray = FOREST.toArray(new AbsTree[FOREST.size()]);
        }
    }

    /** Remove all planted trees. */
    public static void clearTrees() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    /** Return a copy of all planted {@linkplain AbsTree trees}. */
    public static List<AbsTree> forest() {
        synchronized (FOREST) {
            return unmodifiableList(new ArrayList<>(FOREST));
        }
    }

    public static int treeCount() {
        synchronized (FOREST) {
            return FOREST.size();
        }
    }

    private static final AbsTree SOULS_TREE = new AbsTree() {
        //        @Override
        //        public void v(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        //            AbsTree[] forest = forestAsArray;
        //            //noinspection ForLoopReplaceableByForEach
        //            for (int i = 0, count = forest.length; i < count; i++) {
        //                forest[i].v(defaultTag, compoundMsg, normalMsg, args);
        //            }
        //        }

        @Override
        public void v(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            AbsTree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(tag, t, compoundMsg, normalMsg, args);
            }
        }

        //        @Override
        //        public void d(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        //            AbsTree[] forest = forestAsArray;
        //            //noinspection ForLoopReplaceableByForEach
        //            for (int i = 0, count = forest.length; i < count; i++) {
        //                forest[i].d(defaultTag, compoundMsg, normalMsg, args);
        //            }
        //        }

        @Override
        public void d(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            AbsTree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(tag, t, compoundMsg, normalMsg, args);
            }
        }


        @Override
        public void d(String tag, String compoundMsg, @Nullable Object object) {
            AbsTree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(tag, compoundMsg, object);
            }
        }

        //        @Override
        //        public void i(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        //            AbsTree[] forest = forestAsArray;
        //            //noinspection ForLoopReplaceableByForEach
        //            for (int i = 0, count = forest.length; i < count; i++) {
        //                forest[i].i(defaultTag, compoundMsg, normalMsg, args);
        //            }
        //        }

        @Override
        public void i(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            AbsTree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(tag, t, compoundMsg, normalMsg, args);
            }
        }

        //        @Override
        //        public void w(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        //            AbsTree[] forest = forestAsArray;
        //            //noinspection ForLoopReplaceableByForEach
        //            for (int i = 0, count = forest.length; i < count; i++) {
        //                forest[i].w(defaultTag, compoundMsg, normalMsg, args);
        //            }
        //        }

        @Override
        public void w(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            AbsTree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(tag, t, compoundMsg, normalMsg, args);
            }
        }

        //        @Override
        //        public void e(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        //            AbsTree[] forest = forestAsArray;
        //            //noinspection ForLoopReplaceableByForEach
        //            for (int i = 0, count = forest.length; i < count; i++) {
        //                forest[i].e(defaultTag, compoundMsg, normalMsg, args);
        //            }
        //        }

        @Override
        public void e(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            AbsTree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(tag, t, compoundMsg, normalMsg, args);
            }
        }

        //        @Override
        //        public void wtf(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        //            AbsTree[] forest = forestAsArray;
        //            //noinspection ForLoopReplaceableByForEach
        //            for (int i = 0, count = forest.length; i < count; i++) {
        //                forest[i].wtf(defaultTag, compoundMsg, normalMsg, args);
        //            }
        //        }

        @Override
        public void wtf(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
            AbsTree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(tag, t, compoundMsg, normalMsg, args);
            }
        }

        //        @Override
        //        public void log(int priority, String defaultTag, Throwable t, String compoundMsg, @Nullable String normalMsg,
        //                @Nullable Object... args) {
        //            AbsTree[] forest = forestAsArray;
        //            //noinspection ForLoopReplaceableByForEach
        //            for (int i = 0, count = forest.length; i < count; i++) {
        //                forest[i].log(priority, defaultTag, t, compoundMsg, normalMsg, args);
        //            }
        //        }

        @Override
        protected void log(int priority, String tag, String message) {
            throw new UnsupportedOperationException("do not support this method");
        }
    };
}
