package com.example.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты ResultCheck — проверка попадания точки в область")
public class ResultCheckTest {

    @Test
    @DisplayName("Треугольник: точка внутри")
    void testTriangleInside() {
        assertTrue(ResultCheck.resultCheck(0.5, "0.5", "2.0"),
                "Точка (0.5, 0.5) при R=2 должна быть внутри треугольника");
    }

    @Test
    @DisplayName("Треугольник: точка на границе (гипотенуза)")
    void testTriangleBoundary() {
        assertTrue(ResultCheck.resultCheck(1.0, "1.0", "2.0"),
                "Точка (1, 1) при R=2 на гипотенузе y=r-x — должна быть внутри");
    }

    @Test
    @DisplayName("Треугольник: точка вне (выше гипотенузы)")
    void testTriangleOutside() {
        assertFalse(ResultCheck.resultCheck(1.0, "1.5", "2.0"),
                "Точка (1, 1.5) при R=2 выше гипотенузы — промах");
    }


    @Test
    @DisplayName("Прямоугольник: точка внутри")
    void testRectangleInside() {
        assertTrue(ResultCheck.resultCheck(-0.5, "-0.5", "2.0"),
                "Точка (-0.5, -0.5) при R=2 должна быть внутри прямоугольника");
    }

    @Test
    @DisplayName("Прямоугольник: точка на нижней границе")
    void testRectangleLowerBoundary() {
        assertTrue(ResultCheck.resultCheck(-0.5, "-2.0", "2.0"),
                "Точка (-0.5, -2) при R=2 на нижней границе — попадание");
    }

    @Test
    @DisplayName("Прямоугольник: точка за левой границей")
    void testRectangleOutsideLeft() {
        assertFalse(ResultCheck.resultCheck(-1.5, "-0.5", "2.0"),
                "Точка (-1.5, -0.5) при R=2 левее границы x>=-r/2=-1 — промах");
    }


    @Test
    @DisplayName("Полукруг: точка внутри")
    void testSemicircleInside() {
        assertTrue(ResultCheck.resultCheck(1.0, "-1.0", "2.0"),
                "Точка (1, -1) при R=2 внутри полукруга — попадание");
    }

    @Test
    @DisplayName("Полукруг: точка на границе окружности")
    void testSemicircleBoundary() {
        assertTrue(ResultCheck.resultCheck(2.0, "0.0", "2.0"),
                "Точка (2, 0) при R=2 на границе полукруга — попадание");
    }

    @Test
    @DisplayName("Полукруг: точка вне окружности")
    void testSemicircleOutside() {
        assertFalse(ResultCheck.resultCheck(1.5, "-1.5", "2.0"),
                "Точка (1.5, -1.5) при R=2: x?+y?=4.5 > r?=4 — промах");
    }


    @Test
    @DisplayName("Начало координат — попадание")
    void testOrigin() {
        assertTrue(ResultCheck.resultCheck(0.0, "0.0", "2.0"),
                "Начало координат всегда внутри при любом R>0");
    }

    @Test
    @DisplayName("R отрицательный — промах")
    void testNegativeR() {
        assertFalse(ResultCheck.resultCheck(0.0, "0.0", "-1.0"),
                "R <= 0 недопустим — должен вернуть false");
    }

    @Test
    @DisplayName("R = 0 — промах")
    void testZeroR() {
        assertFalse(ResultCheck.resultCheck(0.0, "0.0", "0.0"),
                "R = 0 недопустим — должен вернуть false");
    }

    @Test
    @DisplayName("R > 5 — промах (за пределами допустимого)")
    void testRTooLarge() {
        assertFalse(ResultCheck.resultCheck(1.0, "1.0", "6.0"),
                "R > 5 недопустим — должен вернуть false");
    }

    @Test
    @DisplayName("X за левой границей (-5)")
    void testXTooSmall() {
        assertFalse(ResultCheck.resultCheck(-6.0, "0.0", "2.0"),
                "X < -5 недопустим — должен вернуть false");
    }

    @Test
    @DisplayName("Y за верхней границей (3)")
    void testYTooLarge() {
        assertFalse(ResultCheck.resultCheck(0.0, "4.0", "2.0"),
                "Y > 3 недопустим — должен вернуть false");
    }

    @Test
    @DisplayName("Точка в III четверти вне прямоугольника")
    void testThirdQuadrantOutside() {
        assertFalse(ResultCheck.resultCheck(-3.0, "-3.0", "2.0"),
                "Точка (-3, -3) при R=2 вне всех фигур — промах");
    }

    @Test
    @DisplayName("Точка во II четверти — промах (нет фигуры)")
    void testSecondQuadrant() {
        assertFalse(ResultCheck.resultCheck(-1.0, "1.0", "2.0"),
                "II четверть: нет фигуры при x<0, y>0 — промах");
    }
}