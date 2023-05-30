package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        // Get the origin and direction of the ray
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        int positive = 0, negative = 0,
                verAmount = vertices.size();

        // Loop through each vertex of the polygon
        for (int i = 1; i < verAmount; i++) {

            // Calculate the normal vector to the plane formed by the ray and the two vertices
            Vector Vi = vertices.get(i - 1).subtract(p0);
            Vector Vi1 = vertices.get(i).subtract(p0);

            Vector Ni = Vi.crossProduct(Vi1);
            double vni = v.dotProduct(Ni);

            // If the dot product is zero, the ray is parallel to the plane formed by the two vertices
            if (isZero(vni))
                return null;

            if (vni < 0)
                negative++;
            else positive++;
        }

        // calculate the above for the first and last vertices
        Vector Vn = vertices.get(verAmount - 1).subtract(p0);
        Vector V1 = vertices.get(0).subtract(p0);
        Vector Ni = Vn.crossProduct(V1);
        double vni = v.dotProduct(Ni);

        // If the dot product is zero, the ray is parallel to the plane formed by the two vertices
        if (isZero(vni))
            return null;

        // If the dot products of all the normal vectors and the ray direction have the same sign
        // the ray is intersects the polygon
        if (vni > 0 && positive == verAmount - 1 || vni < 0 && negative == verAmount - 1)
            return plane.findGeoIntersections(ray).stream().map(p -> new GeoPoint(this, p.point)).toList();

        return null;
    }

}
