import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int segmentCount;

    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }

        for (int i = 0; i < points.length; i++) {
            Comparator<Point> comparator = points[i].slopeOrder();
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    if (comparator.compare(points[j], points[k]) == 0) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (comparator.compare(points[l], points[k]) == 0) {
                                Point[] pointArray = new Point[]{points[i], points[j], points[k], points[l]};
                                Arrays.sort(pointArray);
                                LineSegment lineSegment = new LineSegment(pointArray[0], pointArray[3]);
                                addLineSegment(lineSegment);
                            }
                        }
                    }
                }
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

    public static void main(String[] args) {

    }

}
