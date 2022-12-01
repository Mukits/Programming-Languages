package uk.ac.aston.oop.acint.shapes;

import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;

import uk.ac.aston.oop.acint.util.GraphicsContextWrapper;

public class CrossTest {

	private GraphicsContextWrapper gcw;

	@BeforeEach
	public void setup() {
		gcw = mock(GraphicsContextWrapper.class);
		when(gcw.width()).thenReturn(100d);
		when(gcw.height()).thenReturn(200d);
	}
	
	@Test
	public void crossDrawsTwoCrossingLines() {
		Cross c = new Cross(20, 30);
		c.draw(gcw);
		verify(gcw, description("Drawing an X-shaped cross centered at (20, 30), first segment") ).line(
			20 - Cross.SIZE/2, 30 - Cross.SIZE/2,
			20 + Cross.SIZE/2, 30 + Cross.SIZE/2);
		verify(gcw, description("Drawing an X-shaped cross centered at (20, 30), second segment")).line(
			20 + Cross.SIZE/2, 30 - Cross.SIZE/2,
			20 - Cross.SIZE/2, 30 + Cross.SIZE/2);
	}

	@Test
	public void crossMove() {
		Cross c = new Cross(20, 30);
		c.move(gcw, 5, 10);
		c.draw(gcw);
		verify(gcw, description("Moving a cross centered at (20, 30) by 5 to the right and 10 down with screen size of 100 by 200"))
			.line(eq(25.0d - Cross.SIZE/2), eq(40.0d - Cross.SIZE/2), anyDouble(), anyDouble());
	}

	@Test
	public void crossClipX() {
		Cross c = new Cross(20, 30);
		c.move(gcw, 100, 0);
		c.draw(gcw);
		verify(gcw, description("Moving a cross centered at (20, 30) by 100 to the right with screen size of 100 by 200"))
			.line(eq(90.0d - Cross.SIZE/2), eq(30.0d - Cross.SIZE/2), anyDouble(), anyDouble());
	}

	@Test
	public void crossClipY() {
		Cross c = new Cross(20, 30);
		c.move(gcw, 0, 200);
		c.draw(gcw);
		verify(gcw, description("Moving a cross centered at (20, 30) by 200 down with screen size of 100 by 200"))
			.line(eq(20.0d - Cross.SIZE/2), eq(190.0d - Cross.SIZE/2), anyDouble(), anyDouble());
	}
	
}
