package week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by panzer on 3/26/17.
 * Brute Force technique to find collinear points
 */
public class BruteCollinearPoints {

    private LineSegment[] segmentsArray;

    public BruteCollinearPoints(Point[] p) {
        if (p == null)
            throw new NullPointerException();
        Point[] points = p.clone();
        sameCheck(points);
        List<LineSegment> segments = new ArrayList<LineSegment>();
        double slope, tempSlope;
        for (int pi = 0; pi < points.length - 3; pi++) {
            for (int qi = pi+1; qi < points.length - 2; qi++) {
                cornerCheck(points[pi], points[qi]);
                slope = points[pi].slopeTo(points[qi]);
                for (int ri = qi+1; ri < points.length - 1; ri++) {
                    cornerCheck(points[qi], points[ri]);
                    tempSlope = points[qi].slopeTo(points[ri]);
                    if (Double.compare(tempSlope, slope) != 0)
                        continue;
                    for (int si = ri+1; si < points.length; si++) {
                        cornerCheck(points[ri], points[si]);
                        tempSlope = points[ri].slopeTo(points[si]);
                        if (Double.compare(tempSlope, slope) == 0)
                            segments.add(new LineSegment(points[pi], points[si]));
                    }
                }
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

    private void cornerCheck(Point start, Point end) {
        if (start == null || end == null)
            throw new NullPointerException();
        else if (start.compareTo(end) == 0)
            throw new IllegalArgumentException();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
