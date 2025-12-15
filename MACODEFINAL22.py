import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from scipy.integrate import solve_ivp

# Winkel
rad1 = np.pi/4
rad2 = np.pi/2

Pendel1Winkel = rad1 + 0
Pendel2Winkel = rad2 + 0

# Zeit
time = 20

# Parameter
m1, m2 = 1.0, 1.0
l1, l2 = 2.0, 2.0
g = 9.81

def Winkelunterschied(Pendel1Winkel, Pendel2Winkel):
    diff = np.degrees(Pendel1Winkel) - np.degrees(Pendel2Winkel)
    if diff > 0:
        print("Winkelunterschied =", diff)
    elif diff < 0:
        print("Winkelunterschied =", -diff)
    else:
        print("Beide Winkel sind gleich gross")

def Parameter(m1, m2, l1, l2):
    print("Parameter:")
    print("Masse Pendel 1:", m1)
    print("Masse Pendel 2:", m2)
    print("Länge Pendel 1:", l1)
    print("Länge Pendel 2:", l2)

print("Winkel des blauen Pendels [°] =", np.degrees(Pendel1Winkel))
print("Winkel des orangen Pendels [°] =", np.degrees(Pendel2Winkel))
Winkelunterschied(Pendel1Winkel, Pendel2Winkel)
print(" ")
Parameter(m1, m2, l1, l2)
print(" ") 

# Bewegungsgleichungen
def deriv(t, y):
    theta1, theta2, omega1, omega2 = y
    delta = theta1 - theta2
    sin_d = np.sin(delta)
    cos_d = np.cos(delta)
    sin1 = np.sin(theta1)
    sin2 = np.sin(theta2)

    a11 = 1.0
    a12 = (m2 / (m1 + m2)) * (l2 / l1) * cos_d
    a21 = (l1 / l2) * cos_d
    a22 = 1.0
    b1 = - (m2 / (m1 + m2)) * (l2 / l1) * (omega2**2) * sin_d - (g / l1) * sin1
    b2 =   (l1 / l2) * (omega1**2) * sin_d - (g / l2) * sin2

    D = a11 * a22 - a12 * a21
    zahl = 1e-12
    if abs(D) < zahl:
        D = zahl if D == 0 else np.sign(D) * zahl

    alpha1 = (b1 * a22 - b2 * a12) / D
    alpha2 = (a11 * b2 - a21 * b1) / D

    return [omega1, omega2, alpha1, alpha2]

# Anfangsbedingungen
y0 = [Pendel1Winkel, Pendel2Winkel, 0.0, 0.0]
t_span = (0, time)
t_eval = np.linspace(t_span[0], t_span[1], 2000)

# Lyapunov-Exponent Berechnung 
delta0 = 1e-9
y = np.array(y0)
y_p = y + np.array([delta0, delta0, 0, 0])
sum_log = 0.0

for i in range(1, len(t_eval)):
    sol_step = solve_ivp(deriv, [t_eval[i-1], t_eval[i]], y,
                         t_eval=[t_eval[i-1], t_eval[i]], rtol=1e-9, atol=1e-9)
    sol_p_step = solve_ivp(deriv, [t_eval[i-1], t_eval[i]], y_p,
                           t_eval=[t_eval[i-1], t_eval[i]], rtol=1e-9, atol=1e-9)  
    y = sol_step.y[:, -1]
    y_p = sol_p_step.y[:, -1]
    delta_vec = np.linalg.norm(y_p - y)
    if delta_vec == 0:
        delta_vec = 1e-16  
    sum_log += np.log(delta_vec / delta0)    
    y_p = y + delta0 * (y_p - y) / delta_vec

lambda_max = sum_log / time
print("Mittlerer maximaler Lyapunov-Exponent (1/s):", lambda_max)

# Doppelpendel-Lösung
sol = solve_ivp(deriv, t_span, y0, t_eval=t_eval, rtol=1e-9, atol=1e-9)
theta1, theta2 = sol.y[0], sol.y[1]
omega1, omega2 = sol.y[2], sol.y[3]
t = sol.t

# Umrechnung in x,y
x1 = l1*np.sin(theta1)
y1 = -l1*np.cos(theta1)
x2 = x1 + l2*np.sin(theta2)
y2 = y1 - l2*np.cos(theta2)

# Plots
fig, axes = plt.subplots(2, 2, figsize=(12, 10))
ax1, ax2, ax3, ax4 = axes.flatten()

# ax1: Animation
ax1.set_xlim(-(l1+l2+2), l1+l2+2)
ax1.set_ylim(-(l1+l2+2), l1+l2+2)
ax1.set_title("Doppelpendel Animation")
line1, = ax1.plot([], [], 'o-', lw=2, color="blue")
line2, = ax1.plot([], [], 'o-', lw=2, color="orange")
ax1.grid(True)

# ax2: Statischer Anfangszustand
ax2.plot([0, x1[0]], [0, y1[0]], 'o-', lw=2, color="blue")
ax2.plot([x1[0], x2[0]], [y1[0], y2[0]], 'o-', lw=2, color="orange")
ax2.set_xlim(-(l1+l2+2), l1+l2+2)
ax2.set_ylim(-(l1+l2+2), l1+l2+2)
ax2.set_title("Statischer Anfangszustand")
ax2.grid(True)

# ax3: Winkel-Zeit Diagramm 
ax3.plot(t, theta1, label=r'$\theta_1$', color="blue")
ax3.plot(t, theta2, label=r'$\theta_2$', color="orange")
ax3.set_xlabel("Zeit [s]")
ax3.set_ylabel("Winkel [rad]")
ax3.set_title("Winkel-Zeit Diagramm")
ax3.legend()
ax3.grid(True)

# ax4: Phasenraum 
ax4.plot(theta1, omega1, color="blue", label="Pendel 1")
ax4.plot(theta2, omega2, color="orange", label="Pendel 2")
ax4.set_xlabel("Winkel θ [rad]")
ax4.set_ylabel("Winkelgeschwindigkeit ω [rad/s]")
ax4.set_title("Phasenraum")
ax4.legend()
ax4.grid(True)

def update(i):
    line1.set_data([0, x1[i]], [0, y1[i]])
    line2.set_data([x1[i], x2[i]], [y1[i], y2[i]])
    return line1, line2

ani = FuncAnimation(fig, update, frames=len(t_eval), blit=True, interval=10)
plt.tight_layout()
plt.show()

