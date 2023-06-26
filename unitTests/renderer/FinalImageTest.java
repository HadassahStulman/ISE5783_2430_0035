package renderer;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

public class FinalImageTest {
    @Test
    public void FinalImage() {
        Scene scene = new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).build();
        Camera camera = new Camera(new Point(0, 0, 40), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(100)
                .setRayTracer(new RayTracerBasic(scene)).setSuperSampling(1);


        // adding 4 walls, ceiling and floor
        scene.geometries.add(

                // walls and floors
                new Plane(new Point(-45, 0, 0), new Vector(1, 0, 0)) // left wall
                        .setMaterial(new Material().setShininess(60))
                        .setEmission(new Color(255, 150, 255)),
                new Plane(new Point(45, 0, 0), new Vector(-1, 0, 0)) // right wall
                        .setMaterial(new Material().setShininess(60))
                        .setEmission(new Color(150, 0, 255)),
                new Plane(new Point(0, -45, 0), new Vector(0, 1, 0)) // floor
                        .setMaterial(new Material().setKr(0.2).setShininess(60))
                        .setEmission(new Color(GRAY)),
                new Plane(new Point(0, 45, 0), new Vector(0, -1, 0)) // ceiling
                        .setMaterial(new Material().setShininess(60))
                        .setEmission(new Color(BLACK)),
                new Plane(new Point(0, 0, -100), new Vector(0, 0, 1)) // back wall
                        .setMaterial(new Material().setShininess(60))
                        .setEmission(new Color(GRAY).scale(0.7)),

                // mirror
                new Polygon(new Point(-44.9,-15, -10), new Point(-44.9,-15, -70), new Point(-44.9,15, -70), new Point(-44.9,15, -10 ))
                        .setEmission(new Color(WHITE)).setMaterial(new Material().setKr(1).setShininess(100)),


                // floor decoration
                new Polygon(new Point(45, -44.9, -75), new Point(22.5, -44.9, -75), new Point(22.5, -44.9, -50), new Point(45, -44.9, -50))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),
                new Polygon(new Point(45, -44.9, -25), new Point(22.5, -44.9, -25), new Point(22.5, -44.9, 0), new Point(45, -44.9, 0))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),
                new Polygon(new Point(22.5, -44.9, -100), new Point(0, -44.9, -100), new Point(0, -44.9, -75), new Point(22.5, -44.9, -75))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),
                new Polygon(new Point(22.5, -44.9, -50), new Point(0, -44.9, -50), new Point(0, -44.9, -25), new Point(22.5, -44.9, -25))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),
                new Polygon(new Point(0, -44.9, -75), new Point(-22.5, -44.9, -75), new Point(-22.5, -44.9, -50), new Point(0, -44.9, -50))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),
                new Polygon(new Point(0, -44.9, -25), new Point(-22.5, -44.9, -25), new Point(-22.5, -44.9, 0), new Point(0, -44.9, 0))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),
                new Polygon(new Point(-22.5, -44.9, -100), new Point(-45, -44.9, -100), new Point(-45, -44.9, -75), new Point(-22.5, -44.9, -75))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),
                new Polygon(new Point(-22.5, -44.9, -50), new Point(-45, -44.9, -50), new Point(-45, -44.9, -25), new Point(-22.5, -44.9, -25))
                        .setEmission(new Color(BLACK)).setMaterial(new Material().setKr(0.2).setShininess(60)),


                // Far Chandelier
                new Sphere(new Point(0, 28, -65), 4)
                        .setEmission(new Color(255, 235, 50)).setMaterial(new Material().setKt(0.6)), // light bulb
                new Polygon(new Point(-4, 31, -60), new Point(4, 31, -60), new Point(8, 24.5, -60), new Point(-8, 24.5, -60))
                        .setEmission(new Color(BLUE)),  // Chandelier body
                new Polygon(new Point(-0.2, 45, -65), new Point(0.2, 45, -65), new Point(0.2, 30, -65), new Point(-0.2, 30, -65))
                        .setEmission(new Color(BLUE)), // string
                new Sphere(new Point(0, 30, -60), 1.5)
                        .setEmission(new Color(BLUE)),
                new Sphere(new Point(0, 45, -65), 2.5)
                        .setEmission(new Color(BLUE)),

                // Close Chandelier
                new Sphere(new Point(0, 32, -12), 4.5)
                        .setEmission(new Color(255, 235, 50))
                        .setMaterial(new Material().setKt(0.6)), // light bulb
                new Polygon(new Point(-3.5, 31, -3), new Point(3.5, 31, -3), new Point(7, 24.5, -3), new Point(-7.3, 24.5, -3))
                        .setEmission(new Color(BLUE)),  // Chandelier body
                new Polygon(new Point(-0.2, 45, -5), new Point(0.2, 45, -5), new Point(0.2, 30, -5), new Point(-0.2, 30, -5))
                        .setEmission(new Color(BLUE)), // string
                new Sphere(new Point(0, 31.8, -5), 0.9)
                        .setEmission(new Color(BLUE)),
                new Sphere(new Point(0, 46, -5), 2)
                        .setEmission(new Color(BLUE)),


                // playroom toys
                new Sphere(new Point(-22, -29.9, -70), 15)
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.11).setKs(0.00001)),
                        //.setMaterial(new Material().setKr(0.4).setKd(0.1).setShininess(50).setKs(0.7)),
                new Sphere(new Point(-30, -40.5, -20), 4)
                        .setEmission(new Color(YELLOW).reduce(2.5))
                        .setMaterial(new Material().setKd(0.11)),
                new Sphere(new Point(24, -35.5, -12), 9)
                        .setEmission(new Color(BLUE).scale(5))
                        .setMaterial(new Material().setKd(0.12).setKs(0.001).setKt(0.6)),
                new Sphere(new Point(24, -39.5, -36), 5)
                        .setEmission(new Color(255, 87, 51))
                        .setMaterial(new Material().setKs(0.001).setKd(0.081)),
                new Sphere(new Point(0, -34.5, -30), 9).setMaterial(new Material().setKd(0.11).setKs(0.21))
                        .setEmission(new Color(GREEN).reduce(2))
                        .setMaterial(new Material().setKd(0.11).setKs(0.00001)));




        // lighting on far chandelier
//        scene.lights.add(new PointLight(new Color(WHITE), new Point(0, 28, -65)));
//        scene.lights.add(new PointLight(new Color(WHITE), new Point(0, 32, -12)));
//        scene.lights.add(new PointLight(new Color(ORANGE), new Point(0, 32, -12)));
//        scene.lights.add(new PointLight(new Color(red), new Point(0, 32, -12)));
//        scene.lights.add(new PointLight(new Color(RED), new Point(0, 32, -12)));
//        scene.lights.add(new PointLight(new Color(ORANGE), new Point(0, 32, -12)));
//        scene.lights.add(new PointLight(new Color(RED), new Point(0, 32, -12)));
//        scene.lights.add(new PointLight(new Color(WHITE), new Point(0, 32, -12)));
//        scene.lights.add(new DirectionalLight(new Color(YELLOW), new Vector(-22, -56, -50)));
//        scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(-22, -56, -50)));

//        // lighting on close chandelier
        scene.lights.add(new PointLight(new Color(ORANGE), new Point(0, 32, -12)));
        scene.lights.add(new PointLight(new Color(YELLOW), new Point(0, 32, -12)));
        scene.lights.add(new PointLight(new Color(WHITE), new Point(0, 32, -12)));
        scene.lights.add(new PointLight(new Color(ORANGE), new Point(0, 32, -12)));
        scene.lights.add(new PointLight(new Color(RED), new Point(0, 32, -12)));
        scene.lights.add(new PointLight(new Color(YELLOW), new Point(0, 32, -12)));

        // spotlight on green Ball
        scene.lights.add(new SpotLight(new Color(blue), new Point(0,32,-12), new Vector(0, -66.5, -18)));
        scene.lights.add(new SpotLight(new Color(blue), new Point(0,32,-12), new Vector(0, -66.5, -18)));
        scene.lights.add(new SpotLight(new Color(blue), new Point(0,32,-12), new Vector(0, -66.5, -18)));

        // spotlight on blue Ball
        scene.lights.add(new SpotLight(new Color(ORANGE).reduce(2), new Point(0,44,15), new Vector(25,-82.5,-30)));
        scene.lights.add(new SpotLight(new Color(WHITE).reduce(2), new Point(0,44,15), new Vector(25,-82.5,-30)));
        //scene.lights.add(new SpotLight(new Color(RED), new Point(0,44,15), new Vector(25,-82.5,-30)));





        scene.lights.add(new PointLight(new Color(BLUE), new Point(0, 44.9, -48.3)));
        scene.lights.add(new SpotLight(new Color(BLUE), new Point(0, 4, 4), new Vector(0, 30, 0)));
        scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(-29,3.5,-36)));

        ImageWriter imageWriter = new ImageWriter("FinalImage", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
