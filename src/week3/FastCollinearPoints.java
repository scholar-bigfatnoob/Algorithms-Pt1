package week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by panzer on 3/26/17.
 * Faster technique to find collinear points
 */
public class FastCollinearPoints {

    private LineSegment[] segmentsArray;
    
    public FastCollinearPoints(Point[] p) {
        if (p == null)
            throw new NullPointerException();
        Point[] points = p.clone();
        nullCheck(points);
        sameCheck(points);
        List<LineSegment> segments = new ArrayList<LineSegment>();
        Point[] aux = points.clone();
        for (int i = 0; i < aux.length - 3; i++) {
            Arrays.sort(aux);
            Arrays.sort(aux, aux[i].slopeOrder());
            for (int start = 1, end = 2; end < aux.length; end++) {
                while (end < aux.length && Double.compare(aux[0].slopeTo(aux[start]), aux[0].slopeTo(aux[end])) == 0)
                    end++;
                if (end - start >= 3 && aux[0].compareTo(aux[start]) < 0)
                    segments.add(new LineSegment(aux[0], aux[end - 1]));
                start = end;
            }
        }
        segmentsArray = segments.toArray(new LineSegment[segments.size()]);
    }

    public int numberOfSegments() {
        return segmentsArray.length;
    }

    public LineSegment[] segments() {
        return segmentsArray.clone();
    }

    private void nullCheck(Point[] points) {
        for (Point point: points)
            if (point == null)
                throw new NullPointerException();
    }

    private void sameCheck(Point[] points) {
        Arrays.sort(points);
        for (int pi = 1; pi < points.length; pi++)
            if (points[pi].compareTo(points[pi-1]) == 0)
                throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
