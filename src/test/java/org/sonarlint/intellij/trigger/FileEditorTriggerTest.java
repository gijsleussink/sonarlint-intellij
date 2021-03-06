/*
 * SonarLint for IntelliJ IDEA
 * Copyright (C) 2015 SonarSource
 * sonarlint@sonarsource.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonarlint.intellij.trigger;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonarlint.intellij.SonarLintTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FileEditorTriggerTest {
  @Mock
  private Project project;
  @Mock
  private SonarLintSubmitter submitter;

  private FileEditorTrigger editorTrigger;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    SonarLintTestUtils.mockMessageBus(project);
    editorTrigger = new FileEditorTrigger(project, submitter);
  }

  @Test
  public void should_trigger() {
    VirtualFile f1 = mock(VirtualFile.class);
    editorTrigger.fileOpened(mock(FileEditorManager.class), f1);
    verify(submitter).submitFiles(new VirtualFile[] {f1}, TriggerType.EDITOR_OPEN, true, false);
  }

  @Test
  public void should_do_nothing_closed() {
    VirtualFile f1 = mock(VirtualFile.class);
    FileEditorManager mock = mock(FileEditorManager.class);
    editorTrigger.fileClosed(mock, f1);
    editorTrigger.selectionChanged(new FileEditorManagerEvent(mock, null, null, null, null));
  }
}
