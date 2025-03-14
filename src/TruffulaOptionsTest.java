import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TruffulaOptionsTest {

  @Test
  void testValidDirectoryIsSet(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = { "-nc", "-h", directoryPath };

    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);

    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testValidDirectoryIsSetDefault(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = { directoryPath };

    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);

    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertFalse(options.isShowHidden());
    assertTrue(options.isUseColor());
  }

  @Test
    void testOnlyNoColorFlag(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = { "-nc", directoryPath };

    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);

    // Assert: Check that useColor is false 
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertFalse(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testOnlyShowHiddenFlag(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-h", directoryPath };

    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);

    // Assert: Check that showHidden is true
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertTrue(options.isUseColor());
  }

  @Test
  void testFlagsSwitched(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = { "-h", "-nc", directoryPath };

    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);

    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertFalse(options.isUseColor());
  }
  // Test that FileNotFoundException is thrown correctly
  @Test
  void testFileNotFoundExceptionisThrown(@TempDir File tempDir) {
    File directory = new File(tempDir, "subfolder");
    String directoryPath = directory.getAbsolutePath();
    String[] args = { directoryPath };

    FileNotFoundException exception = Assertions.assertThrows(FileNotFoundException.class, () -> {
      TruffulaOptions options = new TruffulaOptions(args);
    });

    Assertions.assertEquals("Directory not found or does not exist", exception.getMessage());
  }

  // Test that IllegalArgumentException is thrown correctly
  @Test
  void testIllegalArgumentExceptionInvalidFlags(@TempDir File tempDir) {
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();

    String directoryPath = directory.getAbsolutePath();
    String[] args = { "", "7", directoryPath };

    IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      TruffulaOptions options = new TruffulaOptions(args);
    });

    Assertions.assertEquals("Invalid flags", exception.getMessage());
  }

  @Test
  void testIllegalArgumentExceptionDuplicateFlags(@TempDir File tempDir) {
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();

    String directoryPath = directory.getAbsolutePath();
    String[] args = { "-h", "-h", directoryPath };

    IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      TruffulaOptions options = new TruffulaOptions(args);
    });

    Assertions.assertEquals("Invalid flags", exception.getMessage());
  }

  @Test
  void testIllegalArgumentExceptionSingleInvalidFlag(@TempDir File tempDir) {
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();

    String directoryPath = directory.getAbsolutePath();
    String[] args = { "-g", directoryPath };

    IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      TruffulaOptions options = new TruffulaOptions(args);
    });

    Assertions.assertEquals("Cannot recognize flag: -g", exception.getMessage());
  }

  @Test
  void testIllegalArgumentExceptionEmptyArgs(@TempDir File tempDir) {
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();

    String directoryPath = directory.getAbsolutePath();
    String[] args = { };

    IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      TruffulaOptions options = new TruffulaOptions(args);
    });

    Assertions.assertEquals("Incorrect number of arguments", exception.getMessage());
  }

  @Test
  void testIllegalArgumentExceptionTooManyArgs(@TempDir File tempDir) {
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();

    String directoryPath = directory.getAbsolutePath();
    String[] args = { "-h", "-nc", "-h", directoryPath };

    IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      TruffulaOptions options = new TruffulaOptions(args);
    });

    Assertions.assertEquals("Incorrect number of arguments", exception.getMessage());
  }

  
}
