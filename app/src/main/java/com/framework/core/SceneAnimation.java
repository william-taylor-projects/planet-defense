package com.framework.core;

import java.util.*;

import com.framework.graphics.Button;
import com.framework.graphics.Image;
import com.framework.graphics.Label;
import com.framework.math.Vector2;
import com.framework.opengl.OpenglImage;
import com.framework.opengl.OpenglLine;
import com.framework.opengl.OpenglText;

public class SceneAnimation {
    private ArrayList<OpenglLine> lines  = new ArrayList<OpenglLine>();
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private ArrayList<Image> images = new ArrayList<Image>();
    private ArrayList<Label> labels = new ArrayList<Label>();
    private Integer stateID = 0;
    private Vector2 velocity;
    private Boolean start;

    public SceneAnimation() {
        start = false;
    }

    public Boolean isRunning() {
        return start;
    }

    public void setState(int i) {
        stateID = i;
        start = false;
    }

    public void setupAnimation(ArrayList<Object> sprites) {
        for(Object obj : sprites) {
            if(obj instanceof Image) {
                OpenglImage img = (OpenglImage)((Image)obj).getRawObject();

                if(img.isVisible()) {
                    images.add((Image)obj);
                }

            } else if(obj instanceof Button) {
                OpenglImage img = (OpenglImage)((Button)obj).getImage();

                if(img.isVisible()) {
                    buttons.add((Button)obj);
                }

            } else if(obj instanceof Label) {
                if(((OpenglText)((Label)obj).getRawGL()).isVisible()) {
                    labels.add((Label)obj);
                }
            } else if(obj instanceof OpenglLine) {
                lines.add((OpenglLine)obj);
            }
        }
    }

    public void setVelocity(Vector2 vector) {
        this.velocity = vector;
    }

    public void update() {
        if(start) {
            Integer elementsToMove = 0;
            Integer elementsMoved = 0;

            if(velocity.getX() > 0) {
                for(Image sprite : images) {
                    sprite.translate(velocity.getX(), velocity.getY());
                    if(sprite.getPosition().getX() > 1280) {
                        ++elementsMoved;
                    }
                }

                for(OpenglLine line : lines) {
                    line.translate(velocity.getX(), velocity.getY());
                    ++elementsMoved;
                }

                for(Button button : buttons) {
                    button.translate(velocity.getX(), velocity.getY());
                    if(button.getPosition().getX() > 1280) {
                        ++elementsMoved;
                    }
                }

                for(Label label : labels) {
                    label.translate(label.getPosition().getX() + velocity.getX(), label.getPosition().getY() + velocity.getY());
                    if(label.getPosition().getX() > 1280) {
                        ++elementsMoved;
                    }
                }
            } else {
                for(Image sprite : images) {
                    sprite.translate(velocity.getX(), velocity.getY());
                    if(sprite.getPosition().getX() + sprite.getSize().getX() < -100) {
                        ++elementsMoved;
                    }
                }

                for(OpenglLine line : lines) {
                    line.translate(velocity.getX(), velocity.getY());
                    ++elementsMoved;
                }

                for(Button button : buttons) {
                    button.translate(velocity.getX(), velocity.getY());
                    if(button.getPosition().getX() + button.getSize().getX() < -100) {
                        ++elementsMoved;
                    }
                }

                for(Label label : labels) {
                    label.translate(label.getPosition().getX() + velocity.getX(), label.getPosition().getY() + velocity.getY());
                    if(label.getPosition().getX() + label.getWidth() < -100) {
                        ++elementsMoved;
                    }
                }
            }

            elementsToMove += lines.size();
            elementsToMove += buttons.size();
            elementsToMove += images.size();
            elementsToMove += labels.size();

            if(elementsMoved >= elementsToMove) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(Image sprite : images) {
                            sprite.reset();
                        }

                        for(Button button : buttons) {
                            button.reset();
                        }

                        for(Label label : labels) {
                            label.reset();
                        }

                        for(OpenglLine line : lines) {
                            line.reset();
                        }

                        buttons.clear();
                        images.clear();
                        labels.clear();
                        lines.clear();

                        velocity.set(0f, 0f);
                        start = false;
                    }

                }, 33);

                SceneManager.get().switchTo(stateID);
                GameObject.enableInput();
            }
        }
    }

    public void beginAnimation() {
        start = true;
        GameObject.disableInput();
        if(velocity == null) {
            velocity = new Vector2(10, 0);
        }
    }
}
