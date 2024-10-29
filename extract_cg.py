import subprocess
import os
from os.path import join

jar_path = join(os.getcwd(), "lib", "sootupcg-1.0-SNAPSHOT-jar-with-dependencies.jar")

input_path = "/Users/palaniappanmuthuraman/WorkSpace/sootupcg/cats_benchmark_jar/CFNE1.jar"  # Replace with the actual path
analysis_type = "CHA"  # or "RTA"

# Run the JAR file with the provided arguments
try:
    subprocess.run(
        ["java", "-jar", jar_path, input_path, analysis_type],
        check=True
    )
    print("JAR executed successfully.")
except subprocess.CalledProcessError as e:
    print("Error executing JAR:", e)
