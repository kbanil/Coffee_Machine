enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction turnLeft() {
        switch (this) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
            default:
                throw new IllegalStateException();
        }
    }

    public Direction turnRight() {
        switch (this) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
            default:
                throw new IllegalStateException();
        }
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}

//Don't change code below

class Move {
    public static void moveRobot(Robot robot, int toX, int toY) {
        Direction toXDirection = findToXDirection(robot, toX);
        Direction toYDirection = findToYDirection(robot, toY);
        Direction alignedDirection = alignRobot(robot, toXDirection, toYDirection);
        int xUnitsToMove = Math.abs(toX - robot.getX());
        int yUnitsToMove = Math.abs(toY - robot.getY());
        switch (alignedDirection) {
            case LEFT:
            case RIGHT:
                stepForward(robot, xUnitsToMove);
                turnOptimallyToY(robot, toYDirection);
                stepForward(robot, yUnitsToMove);
                break;
            case UP:
            case DOWN:
                stepForward(robot, yUnitsToMove);
                turnOptimallyToX(robot, toXDirection);
                stepForward(robot, xUnitsToMove);
        }
    }

    private static void stepForward(Robot robot, int times) {
        for (int i = 0; i < times; ++i) {
            robot.stepForward();
        }
    }

    private static Direction alignRobot(Robot robot, Direction toXDirection, Direction toYDirection) {
        final Direction currentDirection = robot.getDirection();
        switch (currentDirection) {
            case UP:
            case DOWN:
                if (toYDirection == currentDirection) {
                    return currentDirection;
                } else {
                    turnOptimallyToX(robot, toXDirection);
                    return robot.getDirection();
                }
            case LEFT:
            case RIGHT:
                if (toXDirection == currentDirection) {
                    return currentDirection;
                } else {
                    turnOptimallyToY(robot, toYDirection);
                    return robot.getDirection();
                }
        }
        return null;
    }

    private static void turnOptimallyToY(Robot robot, Direction toYDirection) {
        final Direction currentDirection = robot.getDirection();
        // RIGHT and ToY - UP turnLeft
        // RIGHT and ToY - DOWN turnRight
        // LEFT and ToY - UP turnRight
        // LEFT and ToY - DOWN turnLeft
        switch (toYDirection) {
            case UP:
                if (currentDirection == Direction.RIGHT)
                    robot.turnLeft();
                else
                    robot.turnRight();
                break;
            case DOWN:
                if (currentDirection == Direction.LEFT)
                    robot.turnLeft();
                else
                    robot.turnRight();
        }
    }

    private static void turnOptimallyToX(Robot robot, Direction toXDirection) {
        final Direction currentDirection = robot.getDirection();
        // UP and ToX - RIGHT turnRight
        // UP and ToX - LEFT turnLeft
        // DOWN and ToX - Right turnLeft
        // DOWN and ToX - LEFT  turnRight
        switch (toXDirection) {
            case RIGHT:
                if (currentDirection == Direction.UP)
                    robot.turnRight();
                else
                    robot.turnLeft();
                break;
            case LEFT:
                if (currentDirection == Direction.DOWN)
                    robot.turnRight();
                else
                    robot.turnLeft();
                break;
        }
    }

    private static Direction findToXDirection(Robot robot, int toX) {
        if (toX - robot.getX() < 0) return Direction.LEFT;
        return Direction.RIGHT;
    }

    private static Direction findToYDirection(Robot robot, int toY) {
        if (toY - robot.getY() < 0) return Direction.DOWN;
        return Direction.UP;
    }
}

class Robot {
    private int x;
    private int y;
    private Direction direction;

    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    public void stepForward() {
        x += direction.dx();
        y += direction.dy();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}