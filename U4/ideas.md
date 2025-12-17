If the course teacher sees this, I am not starting early, just thinking of possible ideas that I could share with my groupmate once I',m done

Idea 1:
Tower defense
Already built out a similar grid-based system for chess. Obviously would have to change some things, like creating a path for the tower objects.
Wouldn't be hard to pull off, just would be very graphics-heavy and design-heavy. 

```
File Structure: 

# Graphics implementation
engine/
├── interactions
│   ├── Keyboard.java
│   └── Mouse.java
├── logic
│   ├── Clock.java
│   ├── Interval.java
│   ├── Timer.java
│   └── Utils.java
├── window
│   ├── BaseComponent.java
│   ├── BasePanel.java
│   ├── GraphicsContext.java
│   └── Texture.java
└── Window.java

Main.java
public/
├── sprites/
│   └── tower.png # Repeat for all towers. Every tower stored as spritesheet
└── tiles.png  # All floor and GUI tile on one spritesheet


src/
├── Game.java
├── 
└── window
    └── Implementations of Lib
```
todo : figure out some better fle structure ideas