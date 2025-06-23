package model.domain.board;

import java.util.Objects;

public class PositionTurn {
        private final Position position;
        private final Direction in,out;

        public PositionTurn(Position position, Direction in, Direction out) {
            System.out.println("Creating PositionTurn with position: " + position + ", in: " + in + ", out: " + out);
            if ( in == null || out == null) {
                throw new IllegalArgumentException("In and out directions must be defined");
            }
            if (!_isStraight(in,out) && !_isTurn(in,out)) {
                throw new IllegalArgumentException("In and out directions must be either straight or a turn");
            }
            this.position = position;
            this.in = in;
            this.out = out;
        }

        public PositionTurn incrementWithStraight() {
            return new PositionTurn(
                    new Position(
                            position.getX() + getOut().getDx(),
                            position.getY() + getOut().getDy()
                    ),
                    getOut(),getOut());
        }


        public PositionTurn withOutwardsDirection(Direction newOut) {
            return new PositionTurn(position, getIn(), newOut);
        }


        private boolean _isStraight(Direction _in, Direction _out) {
            return _in == _out;
        }

        public boolean isStraight() {
            return _isStraight(getIn(),getOut());
        }

        public boolean isTurn() {
            return _isTurn(getIn(),getOut());
        }

        private boolean _isTurn(Direction in, Direction out) {
            // perpendicular <=> dot-product == 0
            int dot = in.getDx() * out.getDx() + in.getDy() * out.getDy();
            return dot == 0;
        }

        public Position getPosition() {
                return position;
            }

        public Direction getIn() {
            return in;
        }

        public Direction getOut() {
            return out;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PositionTurn pt)) return false;
            return Objects.equals(getPosition(), pt.getPosition()) && Objects.equals(getIn(), pt.getIn()) && Objects.equals(getOut(), pt.getOut());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPosition(), getIn(), getOut());
        }

        @Override
        public String toString() {
            return "PositionTurn{position=" + getPosition() + ", in=" + getIn() + ", out=" + getOut() + "'}'";
        }

}
