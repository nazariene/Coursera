import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int segmentCount;

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }


        for (int i = 0; i < points.length - 1; i++) {
            Comparator<Point> comparator = points[i].slopeOrder();
            Arrays.sort(points, i + 1, points.length, comparator);

            Point secondPoint = points[i + 1];
            double slope = points[i].slopeTo(secondPoint);
            int count = 0;
            int j = i + 2;
            for (j = i + 2; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == slope) {
                    count++;
                } else {
                    if (count >= 2) {
                        Point[] temp = new Point[count + 2];
                        temp[0] = points[i];

                        for (int k = 0; k <= count; k++) {
                            temp[k + 1] = points[j - k - 1];
                        }

                        Arrays.sort(temp);
                        addLineSegment(new LineSegment(temp[0], temp[temp.length - 1]));
                    }
                    count = 0;
                    slope = points[i].slopeTo(points[j]);
                }
            }

            //Meh, too lazy to make it pretty :)
            if (count >= 2) {
                Point[] temp = new Point[count + 2];
                temp[0] = points[i];

                for (int k = 0; k <= count; k++) {
                    temp[k + 1] = points[j - k - 1];
                }

                Arrays.sort(temp);
                addLineSegment(new LineSegment(temp[0], temp[temp.length - 1]));
            }
        }
    }


    private void addLineSegment(LineSegment lineSegment) {
        if (lineSegments == null) {
            lineSegments = new LineSegment[2];
        } else if (segmentCount >= lineSegments.length) {
            LineSegment[] temp = new LineSegment[segmentCount * 2];
            for (int i = 0; i < lineSegments.length; i++) {
                temp[i] = lineSegments[i];
            }

            lineSegments = temp;
        }

        lineSegments[segmentCount] = lineSegment;
        segmentCount++;
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            result[i] = lineSegments[i];
        }
        return result;
    }

   /* public static void main(String[] args) {
        // read the n points from a file
        In in = new In("equidistant.txt");
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
    }*/

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(DPA|DCE)-\\d+");
        Matcher matcher = pattern.matcher("DPA-53081 [backport] Move DPA-Server-Tests-Sanity-SchemaUpgrade tests to sanity-integration-test, rewritten the foreign keys obtaining sql to improve performance");
        Set<String> result = new HashSet<>();
        while(matcher.find()) {
            ((HashSet) result).add(matcher.group());
        }

        System.out.println(result);
    }
}
